package com.example.partial.data

import com.example.partial.domain.Doctor
import com.example.partial.domain.Patient


val patientsList = listOf(
    Patient(id = 1, firstName = "John", lastName = "Doe", age = 30),
    Patient(id = 2, firstName = "Jane", lastName = "Smith", age = 25),
)

val doctorsList = listOf(
    Doctor(id = 1, firstName = "Dr. Carl", lastName = "Johnson", specialty = "Cardiology"),
    Doctor(id = 2, firstName = "Dr. Anna", lastName = "Taylor", specialty = "Neurology"),
)