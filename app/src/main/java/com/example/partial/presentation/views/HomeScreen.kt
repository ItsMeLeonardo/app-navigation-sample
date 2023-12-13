package com.example.partial.presentation.views

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle

import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.partial.R
import com.example.partial.domain.Ingredient
import com.example.partial.service.ia.getIngredientsFromImage
import com.example.partial.service.uploadImageToCloudinary
import com.example.partial.utils.convertBitmapToFile
import com.example.partial.utils.uriToFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onGetIngredient: (
        ingredients: List<Ingredient>,
        imageUrl: String
    ) -> Unit,
) {
    val context = LocalContext.current

    val handleTakePicture: (Bitmap) -> Unit = { bitmap ->
        val imageFile = convertBitmapToFile(context, bitmap)
        uploadImageToCloudinary(imageFile,
            onSuccess = { imageUrl ->
                try {

                    CoroutineScope(Dispatchers.IO).launch {
                        val ingredients = getIngredientsFromImage(imageUrl)
                        withContext(Dispatchers.Main) {
                            onGetIngredient(ingredients, imageUrl)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("IngredientError", "Error al obtener ingredientes: ${e.message}")
                    Toast.makeText(
                        context,
                        "Error al obtener ingredientes: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            },
            onError = { error ->
                Log.e("CloudinaryError", "Error al subir imagen: $error")
                Toast.makeText(context, "Error al subir imagen: $error", Toast.LENGTH_LONG).show()
            }
        )
    }

    val handleImagePicked: (Uri) -> Unit = { uri ->
        val imageFile = uriToFile(context, uri)
        uploadImageToCloudinary(imageFile,
            onSuccess = { imageUrl ->
                try {

                    CoroutineScope(Dispatchers.IO).launch {
                        val ingredients = getIngredientsFromImage(imageUrl)
                        withContext(Dispatchers.Main) {
                            onGetIngredient(ingredients, imageUrl)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("IngredientError", "Error al obtener ingredientes: ${e.message}")
                    Toast.makeText(
                        context,
                        "Error al obtener ingredientes: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            },
            onError = { error ->
                Log.e("CloudinaryError", "Error al subir imagen: $error")
                Toast.makeText(context, "Error al subir imagen: $error", Toast.LENGTH_LONG).show()
            }
        )
    }
    val takePictureLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
            bitmap?.let { handleTakePicture(it) }
        }

    val pickImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { handleImagePicked(it) }
        }

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                takePictureLauncher.launch()
            } else {
                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }



    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                pickImageLauncher.launch("image/*")
            }) {
                Icon(imageVector = Icons.Filled.Folder, contentDescription = "Take a photo")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = { CustomBottomAppBar() },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            TakePhotoButton(
                onClick = {
                    requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            )


        }
    }
}

@Composable
fun CustomBottomAppBar() {
    BottomAppBar(
        modifier = Modifier.background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Home
            AppBarIcon(icon = Icons.Default.Home, label = "Home")

            // Favorite
            AppBarIcon(icon = Icons.Default.Favorite, label = "Favorite")


            // Spacer(modifier = Modifier.width(48.dp))


            // Shop List
            AppBarIcon(icon = Icons.Default.ShoppingBag, label = "Shop List")

            // Account
            AppBarIcon(icon = Icons.Default.AccountCircle, label = "Account")
        }


    }
}

@Composable
fun AppBarIcon(
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.Gray
            )
        }
        Text(
            text = label,
            fontSize = 10.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun TakePhotoButton(
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(400.dp)
            .background(Color.Transparent)
            .clickable {
                onClick()
            }
    ) {
        val baseColor = Color(0xFFDD4343)
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(baseColor.copy(alpha = 0.2f), shape = CircleShape)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .background(baseColor.copy(alpha = 0.2f), shape = CircleShape)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .background(baseColor.copy(alpha = 0.2f), shape = CircleShape)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .background(baseColor.copy(alpha = 0.5f), shape = CircleShape)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.ic_camera),
                            contentDescription = "Take a photo",
                            modifier = Modifier
                                .size(120.dp)
                                .fillMaxSize()
                        )
                    }
                }
            }
        }


        Text(
            text = "Take a photo",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen { ingredients, imageUrl ->
        Log.d("HomeScreenPreview", "Ingredients: $ingredients")
        Log.d("HomeScreenPreview", "ImageUrl: $imageUrl")
    }
}