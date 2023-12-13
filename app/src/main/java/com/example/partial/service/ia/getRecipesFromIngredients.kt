package com.example.partial.service.ia

import android.util.Log
import com.example.partial.domain.Ingredient
import com.example.partial.domain.Recipe
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit


private const val OPENAI_URL = "https://api.openai.com/v1/chat/completions"
private const val API_KEY = "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"

fun getRecipesFromIngredients(
    ingredients: List<String>
): List<Recipe> {
    val client = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()


    val ingredientsText = ingredients.joinToString(", ")
    val messageContent = """
    Using the ingredients: $ingredientsText, suggest a list of simple recipes that should primarily use these ingredients. Each recipe should include at least one or two ingredients from the provided list marked as 'obtained': true. Only add minimal additional ingredients if absolutely necessary. Please format the response in JSON format as follows:
    
    {
      "recipes": [
        {
          "id": 1,
          "name": "Recipe Name",
          "ingredients": [
            {"name": "Ingredient from list", "obtained": true},
            {"name": "Additional Ingredient (if necessary)", "obtained": false}
            // Additional ingredients as minimally needed
          ]
        },
        // Add more recipes as needed
      ]
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

            val recipes = parseRecipeResponse(recipesText)

            Log.d("RECIPES-OBJ", recipes.toString())

            return recipes
        } ?: throw Exception("Failed to read response body")
    }
}


fun parseRecipeResponse(response: String): List<Recipe> {
    val recipesJson = JSONObject(response)
    val recipesArray = recipesJson.getJSONArray("recipes")
    val recipes = mutableListOf<Recipe>()

    for (i in 0 until recipesArray.length()) {
        val recipeJson = recipesArray.getJSONObject(i)
        val recipeId = recipeJson.getInt("id")
        val recipeName = recipeJson.getString("name")
        val ingredientsJsonArray = recipeJson.getJSONArray("ingredients")
        val ingredients = mutableListOf<Ingredient>()

        for (j in 0 until ingredientsJsonArray.length()) {
            val ingredientJson = ingredientsJsonArray.getJSONObject(j)
            val ingredientName = ingredientJson.getString("name")
            val ingredientObtained = ingredientJson.getBoolean("obtained")
            ingredients.add(Ingredient(name = ingredientName, obtained = ingredientObtained))
        }

        recipes.add(
            Recipe(
                id = recipeId,
                name = recipeName,
                ingredients = ingredients
            )
        )
    }

    return recipes
}