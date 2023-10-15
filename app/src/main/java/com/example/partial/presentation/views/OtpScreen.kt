package com.example.partial.presentation.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.partial.presentation.viewmodels.OtpViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpScreen(viewModel: OtpViewModel, onOtpValid: () -> Unit) {
    val context = LocalContext.current


    DisposableEffect(Unit) {
        val generatedOtp = viewModel.getGeneratedOtp()


        Log.d("OTP", "Generated OTP: $generatedOtp")

        Toast.makeText(context, "Generated OTP: $generatedOtp", Toast.LENGTH_LONG)
            .show()
        onDispose { }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enter the OTP sent to your notifications",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(16.dp))


        TextField(
            value = viewModel.enteredOtp.value,
            onValueChange = { viewModel.enteredOtp.value = it },
            label = { Text("OTP") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            maxLines = 1,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {

                if (viewModel.isOtpValid()) {
                    onOtpValid()
                } else {
                    Toast.makeText(context, "Incorrect OTP! Please try again.", Toast.LENGTH_SHORT)
                        .show()
                }
            },

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFDD4343),
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(12.dp),

            ) {
            Text("Verify OTP")
        }

    }
}

@Preview
@Composable
fun OtpScreenPreview() {
    val mockViewModel = OtpViewModel()
    OtpScreen(viewModel = mockViewModel, onOtpValid = {})
}
