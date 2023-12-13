package com.example.partial.service.ia

import android.util.Log
import com.example.partial.domain.Ingredient
import com.example.partial.domain.Recipe
import com.example.partial.domain.RecipeDetail
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit


private const val OPENAI_URL = "https://api.openai.com/v1/chat/completions"
private const val API_KEY = "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"

fun getRecipeDetail(
    recipe: Recipe
): RecipeDetail {
    val client = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

    val ingredientsText = recipe.ingredients.joinToString(", ") { "\"${it.name}\"" }
    val messageContent = """
    Provide detailed information for the following recipe, including preparation steps, cooking time, and calorie count. Format the response in JSON as follows:

    {
      "id": ${recipe.id},
      "name": "${recipe.name}",
      "description": "Short description of the recipe"
      
      "steps": ["Step 1", "Step 2", ...],
      "time": Time in minutes,
      "kcal": Calorie count
    }

    """.trimIndent()


    val messages = JSONArray().apply {
        put(JSONObject().apply {
            put("role", "system")
            put("content", "You are a helpful assistant.")
        })
        put(JSONObject().apply {
            put("role", "user")
            put("content", messageContent)
        })
    }

    val json = JSONObject().apply {
        put("model", "gpt-3.5-turbo")
        put("messages", messages)
        put("max_tokens", 500)
        put("temperature", 0.7)
    }

    val body = json.toString().toRequestBody("application/json".toMediaType())

    val request = Request.Builder()
        .url(OPENAI_URL)
        .post(body)
        .addHeader("Authorization", "Bearer $API_KEY")
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) {
            throw Exception("Request failed with code: ${response.code}, Response: ${response.body?.string()}")
        }

        response.body?.string()?.let { responseBody ->
            val responseJson = JSONObject(responseBody)
            val recipesText =
                responseJson.getJSONArray("choices").getJSONObject(0).getJSONObject("message")
                    .getString("content")
            Log.d("INGREDIENTS", ingredientsText)
            Log.d("RECIPES", recipesText)

            val recipes = parseRecipeDetailResponse(recipesText)

            Log.d("RECIPES-OBJ", recipes.toString())

            return recipes
        } ?: throw Exception("Failed to read response body")
    }
}


fun parseRecipeDetailResponse(response: String): RecipeDetail {
    val json = JSONObject(response)
    val id = json.getInt("id")
    val name = json.getString("name")
    val description = json.getString("description")

    val steps = mutableListOf<String>()
    val stepsArray = json.getJSONArray("steps")
    for (i in 0 until stepsArray.length()) {
        steps.add(stepsArray.getString(i))
    }
    val time = json.getInt("time")
    val kcal = json.getInt("kcal")

    return RecipeDetail(id, name, description, steps, time, kcal)
}