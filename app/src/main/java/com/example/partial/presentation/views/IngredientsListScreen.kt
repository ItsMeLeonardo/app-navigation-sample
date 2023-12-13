package com.example.partial.presentation.views

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.partial.domain.Ingredient
import com.example.partial.domain.Recipe
import com.example.partial.service.ia.getRecipesFromIngredients
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable

fun IngredientListScreen(
    photoUrl: String,
    ingredients: List<Ingredient>,
    onGetRecipes: (recipes: List<Recipe>) -> Unit
) {

    fun handleGetRecipesClick(ingredients: List<Ingredient>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val recipes = getRecipesFromIngredients(ingredients.map { it.name })
                Log.d("RecipeListScreen", recipes.toString())

                withContext(Dispatchers.Main) {
                    onGetRecipes(recipes)
                }


            } catch (e: Exception) {
                println(e.printStackTrace())
                Log.e("RecipeListScreenError", e.toString())

                withContext(Dispatchers.Main) {
                    // Manejar el error en la UI
                }
            }
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AsyncImage(
            model = photoUrl,
            contentDescription = "Fridge",
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Título para la lista de ingredientes
        Text(
            text = "Ingredientes Encontrados",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )


        ingredients.forEach { ingredient ->
            IngredientItem(
                ingredient.name
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                handleGetRecipesClick(ingredients)
//                handleGetRecipesClick(defaultIngredients)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 20.dp)
        ) {
            Text("Continuar", color = Color.Black)
        }
    }
}


@Composable
fun IngredientItem(ingredient: String) {
    val isChecked = remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Checkbox(
            checked = isChecked.value,
            onCheckedChange = { isChecked.value = it },
            colors = CheckboxDefaults.colors(checkmarkColor = Color.White)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = ingredient)
        Spacer(modifier = Modifier.weight(1f))
        if (isChecked.value) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Checked",
                tint = Color.Red
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeListScreenPreview() {
    IngredientListScreen(
        photoUrl = "https://i.pinimg.com/236x/92/c3/7e/92c37ecd8faec7dfbfcd5b5114726eed.jpg",
        onGetRecipes = {},
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
                name = "Olive Oil",
            ),
        )
    )
}