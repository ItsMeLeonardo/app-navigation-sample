package com.example.partial.presentation.views

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.partial.data.doctorsList
import com.example.partial.domain.Doctor

@Composable
fun DoctorsListScreen() {
    LazyColumn {
        items(doctorsList) { doctor ->
            DoctorItem(doctor)
        }
    }
}

@Composable
fun DoctorItem(doctor: Doctor) {
    Text(text = "${doctor.firstName} ${doctor.lastName}, Specialty: ${doctor.specialty}")
    // Puedes agregar más detalles o estilos al elemento aquí.
}

@Preview
@Composable
fun DoctorsListScreenPreview() {
    DoctorsListScreen()
}