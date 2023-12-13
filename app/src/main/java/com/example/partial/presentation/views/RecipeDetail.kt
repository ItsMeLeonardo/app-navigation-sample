package com.example.partial.presentation.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PunchClock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.partial.domain.RecipeDetail

@Composable
fun RecipeDetailScreen(
    recipe: RecipeDetail
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = "url_de_tu_imagen", // Reemplaza esto con la URL de tu imagen
            contentDescription = "Recipe Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Icon(Icons.Default.PunchClock, contentDescription = "Time")
                Spacer(modifier = Modifier.width(4.dp))
                Text("${recipe.time} min")
                Spacer(modifier = Modifier.width(16.dp))
                Icon(Icons.Default.Favorite, contentDescription = "Kcal")
                Spacer(modifier = Modifier.width(4.dp))
                Text("${recipe.kcal} kcal")
            }
            Text(
                text = recipe.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = "Steps",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 16.dp)
            )
            recipe.steps.forEachIndexed { _, step ->
                Text(
                    text = step,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailScreenPreview() {
    RecipeDetailScreen(
        recipe = RecipeDetail(
            id = 1,
            name = "Pasta",
            description = "Pasta with tomato sauce",
            time = 30,
            kcal = 300,
            steps = listOf("Step 1", "Step 2", "Step 3"),
        )
    )
}