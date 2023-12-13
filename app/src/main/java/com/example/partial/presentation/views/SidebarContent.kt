package com.example.partial.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.partial.domain.SubscriptionType
import com.example.partial.presentation.navigation.ScreenRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SidebarContent(navController: NavController, subscriptionType: SubscriptionType) {

    val sidebarItems = when (subscriptionType) {
        SubscriptionType.FREE -> listOf(
            "Home",
            "Settings",
            "Help",
            "Logout",
        )

        SubscriptionType.PREMIUM -> listOf(
            "Home",
            "Schedule",
            "Shop List",
            "Settings",
            "Help",
            "Logout",
        )
    }
    val selectedItem = remember { mutableStateOf(sidebarItems[0]) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(text = "FOODFY", fontSize = 32.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(32.dp))

        sidebarItems.forEach { item ->
            NavigationDrawerItem(label = {
                Text(text = item, fontSize = 16.sp)
            },
                selected = item == selectedItem.value,
                onClick = {
                    when (item) {
                        "Home" -> navController.navigate(ScreenRoutes.Home)
                        "Logout" -> navController.navigate(ScreenRoutes.Login)

                    }
                })
            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

@Preview
@Composable
fun SidebarContentPreview() {
    SidebarContent(
        navController = NavController(LocalContext.current),
        subscriptionType = SubscriptionType.FREE
    )
}