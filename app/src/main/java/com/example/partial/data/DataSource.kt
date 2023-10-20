package com.example.partial.data

import com.example.partial.domain.Doctor
import com.example.partial.domain.Patient
import com.example.partial.domain.SubscriptionType
import com.example.partial.domain.User


val patientsList = listOf(
    Patient(id = 1, firstName = "John", lastName = "Doe", age = 30),
    Patient(id = 2, firstName = "Jane", lastName = "Smith", age = 25),
)

val doctorsList = listOf(
    Doctor(id = 1, firstName = "Dr. Carl", lastName = "Johnson", specialty = "Cardiology"),
    Doctor(id = 2, firstName = "Dr. Anna", lastName = "Taylor", specialty = "Neurology"),
)

val userList = listOf(
    User(
        id = 1,
        username = "john_doe",
        email = "jhon@gmail.com",
        password = "\$2a\$10\$CqWaZIpNIpIWh12wcVqrz.vX6kWox6g9VUIVpGH/tCKWzGgx.p.uG", // PXdC0^VK
        SubscriptionType.FREE
    ),

    User(
        id = 2,
        username = "jane_smith",
        email = "jane@gmail.com",
        password = "\$2a\$10\$jB/T2HIITPPyDFNAKr9q9.b6NGCQTG2J4H8q9gBmh.d0ApBEPfuFG", // 2a)Um_EDOV
        SubscriptionType.PREMIUM
    )
)