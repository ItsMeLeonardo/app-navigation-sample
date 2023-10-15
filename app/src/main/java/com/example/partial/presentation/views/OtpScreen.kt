package com.example.partial.presentation.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.partial.presentation.viewmodels.OtpViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpScreen(viewModel: OtpViewModel, onOtpValid: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Enter the OTP sent to your notifications")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.enteredOtp.value,
            onValueChange = { viewModel.enteredOtp.value = it },
            label = { Text("OTP") },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (viewModel.isOtpValid()) {
                onOtpValid()
            } else {
                // TODO: Mostrar mensaje de error
            }
        }) {
            Text(text = "Verify OTP")
        }
    }
}

@Preview
@Composable
fun OtpScreenPreview() {
    val mockViewModel = OtpViewModel()
    OtpScreen(viewModel = mockViewModel, onOtpValid = {})
}
