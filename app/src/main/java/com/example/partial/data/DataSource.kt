package com.example.partial.data

import com.example.partial.domain.SubscriptionType
import com.example.partial.domain.User


val userList = listOf(
    User(
        id = 1,
        username = "john_doe",
        email = "jhon@gmail.com",
        password = "\$2a\$10\$CqWaZIpNIpIWh12wcVqrz.vX6kWox6g9VUIVpGH/tCKWzGgx.p.uG", // PXdC0^VK
        SubscriptionType.PREMIUM
    ),

    User(
        id = 2,
        username = "jane_smith",
        email = "jane@gmail.com",
        password = "\$2a\$10\$jB/T2HIITPPyDFNAKr9q9.b6NGCQTG2J4H8q9gBmh.d0ApBEPfuFG", // 2a)Um_EDOV
        SubscriptionType.PREMIUM
    )
)