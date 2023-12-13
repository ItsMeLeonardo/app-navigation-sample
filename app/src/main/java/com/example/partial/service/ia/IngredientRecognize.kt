package com.example.partial.service.ia

import com.example.partial.domain.Ingredient
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*


suspend fun getIngredientsFromImage(imageUrl: String): List<Ingredient> {
    val client = HttpClient()
    val prompt = "Using a comma-separated format, list the ingredients visible in this image along with their estimated quantities. If exact quantities are not clear, provide a general estimate without additional explanations. Example format: 'about 7 tomatoes, estimated 1 head of lettuce, roughly 3 red bell peppers'."
    client.use { client ->
        val response: HttpResponse = client.post("https://api.openai.com/v1/chat/completions") {
            header("Authorization", "Bearer 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")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            body = """
                {
                    "model": "gpt-4-vision-preview",
                    "messages": [
                        {
                            "role": "user",
                            "content": [
                                {"type": "text", "text": "$prompt"},
                                {"type": "image_url", "image_url": {"url": "$imageUrl"}}
                            ]
                        }
                    ]
                }
            """.trimIndent()
        }

        if (response.status == HttpStatusCode.OK) {
            val jsonResponse = Json.parseToJsonElement(response.readText())

            return extractIngredients(jsonResponse)
        } else {
            throw Exception("Error al procesar la imagen: ${response.status.description}")
        }
    }
}

fun extractIngredients(jsonResponse: JsonElement): List<Ingredient> {
    val ingredients = mutableListOf<Ingredient>()
    jsonResponse.jsonObject["choices"]?.jsonArray?.forEach { choice ->
        val text = choice.jsonObject["message"]?.jsonObject?.get("content")?.jsonPrimitive?.content ?: ""
        text.split(",").forEach { ingredient ->
            val nameWithQuantity = ingredient.trim()
            val name = nameWithQuantity.replace(Regex("estimated|about|roughly|around|approximately"), "").trim()
            if (name.isNotEmpty()) {
                ingredients.add(Ingredient(name = name))
            }
        }
    }
    return ingredients
}

