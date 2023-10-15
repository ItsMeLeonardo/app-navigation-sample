package com.example.partial.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.partial.presentation.navigation.ScreenRoutes

@Composable
fun SidebarContent(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDEDED))
            .padding(16.dp)
    ) {
        SidebarItem(
            label = "Patients",
            onClick = { navController.navigate(ScreenRoutes.PatientsList) })
        Spacer(modifier = Modifier.height(8.dp))
        SidebarItem(
            label = "Doctors",
            onClick = { navController.navigate(ScreenRoutes.DoctorsList) })
        Spacer(modifier = Modifier.height(8.dp))
        SidebarItem(label = "Logout", onClick = { })
        Spacer(modifier = Modifier.height(8.dp))
        SidebarItem(label = "Settings", onClick = { })
        Spacer(modifier = Modifier.height(8.dp))
        SidebarItem(label = "Help", onClick = { })
    }
}

@Composable
fun SidebarItem(label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // Icono (por ahora no hay icono, pero puedes agregar uno en el futuro si lo deseas)
        Spacer(modifier = Modifier.width(32.dp))
        Text(text = label)
    }
}

@Preview
@Composable
fun SidebarContentPreview() {
    SidebarContent(
        navController = NavController(LocalContext.current)
    )
}