package com.example.partial.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.partial.presentation.views.*

@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    ScaffoldWithDrawer(navController = navController) {
        NavHost(navController = navController, startDestination = ScreenRoutes.Home) {
            composable(ScreenRoutes.Home) {
                HomeScreen()
            }
            composable(ScreenRoutes.PatientsList) {
                PatientsListScreen()
            }
            composable(ScreenRoutes.DoctorsList) {
                DoctorsListScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithDrawer(navController: NavController, content: @Composable () -> Unit) {
    ModalNavigationDrawer(
        drawerContent = {
            SidebarContent(navController = navController)
        },
        content = {
            content()
        }
    )
}
