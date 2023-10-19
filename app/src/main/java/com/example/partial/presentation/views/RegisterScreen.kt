package com.example.partial.presentation.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.partial.domain.repository.InMemoryUserRepository
import com.example.partial.domain.security.generateSecurePassword
import com.example.partial.presentation.viewmodels.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel, onRegisterSuccess: () -> Unit,
    onGoToLogin: () -> Unit
) {

    val context = LocalContext.current

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "FOODFY", fontSize = 32.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(32.dp))

            Text(text = "Register", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)

            Spacer(modifier = Modifier.height(32.dp))

            TextField(
                value = viewModel.username.value,
                onValueChange = { viewModel.username.value = it },
                label = { Text("Username") },
                maxLines = 1,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(8.dp),

                )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = viewModel.email.value,
                onValueChange = { viewModel.email.value = it },
                label = { Text("Email") },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(8.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = viewModel.password.value,
                onValueChange = { viewModel.password.value = it },
                label = { Text("Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                maxLines = 1,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(8.dp),
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),

                trailingIcon = {
                    Row(
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {

                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = "Suggest secure password",
                            modifier = Modifier
                                .clickable {
                                    val securePassword = generateSecurePassword(10)
                                    viewModel.password.value = securePassword
                                    passwordVisible.value = true
                                }
                                .padding(12.dp)
                                .size(24.dp)
                        )


                        val imageVector: ImageVector =
                            if (passwordVisible.value) Icons.Default.VisibilityOff
                            else Icons.Default.Visibility
                        Icon(
                            imageVector = imageVector,
                            contentDescription = if (passwordVisible.value) "Ocultar contraseña" else "Mostrar contraseña",
                            modifier = Modifier
                                .clickable { passwordVisible.value = !passwordVisible.value }
                                .padding(12.dp)
                                .size(24.dp)
                        )
                    }

                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (viewModel.isPasswordSecure(viewModel.password.value)) {
                        if (viewModel.register()) {
                            onRegisterSuccess()
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit and one special character.",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFDD4343),
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(12.dp)
            ) {
                Text(text = "Create account")

            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = viewModel.registrationStatus.value ?: "")

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onGoToLogin) {
                Text(text = "Already have an account? Login here.")
            }
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(viewModel = RegisterViewModel(
        InMemoryUserRepository()
    ), onRegisterSuccess = {},
        onGoToLogin = {})
}