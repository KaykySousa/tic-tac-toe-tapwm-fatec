package com.example.app

import androidx.compose.material3.Scaffold
import com.example.app.ui.theme.AppTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.app.ui.Destinations
import com.example.app.ui.NavGraph
import com.google.firebase.auth.FirebaseAuth


@Composable
fun App(navController: NavHostController = rememberNavController()) {

    val auth = FirebaseAuth.getInstance()
    val startNavigation = if (auth.currentUser != null) Destinations.HOME else Destinations.AUTH

    AppTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
        ) { innerPadding ->
            NavGraph(
                navController = navController,
                startDestination = startNavigation,
                modifier = Modifier
                    .padding(innerPadding)
            )
        }
    }
}
