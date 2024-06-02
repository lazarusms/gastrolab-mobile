package com.example.gastrolab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun CustomApp(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0B1C5A)),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        IconButton(onClick = { navController.navigate("home") }) {
            Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.White)
        }
    }
}