package com.example.partial.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.partial.presentation.viewmodels.LoginViewModel
import com.example.partial.presentation.viewmodels.OtpViewModel
import com.example.partial.presentation.views.*
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.Color


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val currentRoute = remember { mutableStateOf<String?>(null) }

    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            currentRoute.value = destination.route
        }

        navController.addOnDestinationChangedListener(listener)

        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }

    val isDrawerEnabled =
        currentRoute.value !in listOf(ScreenRoutes.Login, ScreenRoutes.OtpVerification)

    ScaffoldWithDrawer(
        navController = navController, drawerState = drawerState,
        isDrawerEnabled = isDrawerEnabled
    ) {
        NavHost(navController = navController, startDestination = ScreenRoutes.Login) {
            composable(ScreenRoutes.Home) {
                HomeScreen()
            }
            composable(ScreenRoutes.PatientsList) {
                PatientsListScreen()
            }
            composable(ScreenRoutes.DoctorsList) {
                DoctorsListScreen()
            }
            composable(ScreenRoutes.Login) {
                val viewModel = remember { LoginViewModel() }
                LoginScreen(
                    viewModel = viewModel,
                    onLoginSuccess = {
                        navController.navigate(ScreenRoutes.OtpVerification)
                    })
            }

            composable(ScreenRoutes.OtpVerification) {
                val mockViewModel = remember { OtpViewModel() }
                OtpScreen(mockViewModel, onOtpValid = {
                    navController.navigate(ScreenRoutes.Home)
                })
            }


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithDrawer(
    navController: NavController,
    drawerState: DrawerState,
    isDrawerEnabled: Boolean,
    content: @Composable () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            if (isDrawerEnabled) {
                ModalDrawerSheet {
                    SidebarContent(navController = navController)
                }
            } else {
                Box(modifier = Modifier.fillMaxSize())
            }
        },
        content = {
            Column {
                if (isDrawerEnabled) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "🍔",
                            fontSize = 24.sp,
                            modifier = Modifier.clickable {

                                coroutineScope.launch {
                                    if (drawerState.isOpen) {
                                        drawerState.close()
                                    } else {
                                        drawerState.open()
                                    }
                                }
                            }
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Color(0xFFEDEDED)
                        )
                ) {
                    content()
                }
            }
        }
    )
}
