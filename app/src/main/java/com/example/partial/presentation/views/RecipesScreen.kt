package com.example.partial.presentation.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight

import com.example.partial.domain.Ingredient
import com.example.partial.domain.Recipe
import com.example.partial.domain.RecipeDetail
import com.example.partial.service.ia.getRecipeDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun RecipesScreen(
    recipes: List<Recipe>,
    onRecipeSelected: (recipe: RecipeDetail) -> Unit
) {
    val context = LocalContext.current

    fun handleSelectRecipe(recipe: Recipe) {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                val recipeDetail = getRecipeDetail(recipe)

                withContext(Dispatchers.Main) {
                    onRecipeSelected(recipeDetail)
                }

            } catch (e: Exception) {
                println(e.printStackTrace())
                Log.e("RecipeListScreenError", e.toString())

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error getting recipe detail", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Recipes",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyColumn {
            items(recipes) { recipe ->
                RecipeItem(recipe = recipe, onClick = {
                    handleSelectRecipe(recipe)
                })
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipeItem(
    recipe: Recipe,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFEAECF3))
            .fillMaxWidth()
            .clickable {
                onClick()
            },

        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.titleMedium
            )

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                recipe.ingredients.forEach { ingredient ->
                    Text(
                        text = ingredient.name,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = if (ingredient.obtained) FontWeight.Normal else FontWeight.Bold,
                        color = if (ingredient.obtained) Color.Black else Color(0xFFD25E62),
                        modifier = Modifier
                            .padding(end = 8.dp)
                    )
                }
            }

        }
        Row(
            modifier = Modifier
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Go to recipe details",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeScreenPreview() {
    val defaultList = listOf(
        Recipe(
            id = 1,
            name = "Tomato soup",
            ingredients = listOf(
                Ingredient(
                    name = "Tomato",
                ),
                Ingredient(
                    name = "Onion",
                ),
                Ingredient(
                    name = "Garlic",
                ),
                Ingredient(
                    name = "Salt",
                ),
                Ingredient(
                    name = "Pepper",
                ),
                Ingredient(
                    name = "Water",
                ),
            )
        ),
        Recipe(
            id = 2,
            name = "Tomato soup",
            ingredients = listOf(
                Ingredient(
                    name = "Tomato",
                ),
                Ingredient(
                    name = "Onion",
                ),
                Ingredient(
                    name = "Garlic",
                ),
                Ingredient(
                    name = "Salt",
                ),
                Ingredient(
                    name = "Pepper",
                ),
                Ingredient(
                    name = "Water",
                ),
            )
        ),
        Recipe(
            id = 3,
            name = "Tomato soup",
            ingredients = listOf(
                Ingredient(
                    name = "Tomato",
                ),
                Ingredient(
                    name = "Onion",
                ),
                Ingredient(
                    name = "Garlic",
                ),
                Ingredient(
                    name = "Salt",
                ),
                Ingredient(
                    name = "Pepper",
                ),
                Ingredient(
                    name = "Water",
                ),
            )
        ),
        Recipe(
            id = 4,
            name = "Tomato soup",
            ingredients = listOf(
                Ingredient(
                    name = "Tomato",
                ),
                Ingredient(
                    name = "Onion",
                ),
                Ingredient(
                    name = "Garlic",
                ),
                Ingredient(
                    name = "Salt",
                ),
                Ingredient(
                    name = "Pepper",
                ),
                Ingredient(
                    name = "Water",
                ),
            )
        ),
        Recipe(
            id = 5,
            name = "Tomato soup",
            ingredients = listOf(
                Ingredient(
                    name = "Tomato",
                ),
                Ingredient(
                    name = "Onion",
                ),
                Ingredient(
                    name = "Garlic",
                ),
                Ingredient(
                    name = "Salt",
                ),
                Ingredient(
                    name = "Pepper",
                ),

                )
        ),
        Recipe(
            id = 6,
            name = "Tomato soup",
            ingredients = listOf(
                Ingredient(
                    name = "Tomato",
                ),
                Ingredient(
                    name = "Onion",
                ),
                Ingredient(
                    name = "Garlic",
                ),
                Ingredient(
                    name = "Salt",
                ),
                Ingredient(
                    name = "Pepper",
                ),
                Ingredient(
                    name = "Water",
                ),
            )
        ),
        Recipe(
            id = 7,
            name = "Tomato soup",
            ingredients = listOf(
                Ingredient(
                    name = "Tomato",
                ),
                Ingredient(
                    name = "Onion",
                ),
                Ingredient(
                    name = "Garlic",
                ),
                Ingredient(
                    name = "Salt",
                ),

                )
        ),

        )
    RecipesScreen(
        recipes = defaultList,
        onRecipeSelected = {}
    )
}