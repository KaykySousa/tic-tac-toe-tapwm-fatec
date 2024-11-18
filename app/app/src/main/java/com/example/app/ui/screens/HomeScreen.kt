package com.example.app.ui.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.example.app.ui.Destinations
import com.example.app.ui.theme.Amber500
import com.example.app.ui.theme.Purple700
import com.example.app.ui.viewmodels.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current as Activity
    val coroutineScope = rememberCoroutineScope()

    fun handlePlay() {
        Toast.makeText(context, "Buscando jogo...", Toast.LENGTH_LONG).show()
        coroutineScope.launch {
            homeViewModel.start(
                onFindGame = { gameId ->
                    navController.navigate(Destinations.BOARD(gameId = gameId))
                }
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Purple700)
            .padding(20.dp)
            .then(modifier),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { handlePlay() },
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors().copy(
                contentColor = Color.White,
                containerColor = Amber500
            ),
            contentPadding = PaddingValues(40.dp, 24.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp,
            )
        ) {
            Text(
                text = "JOGAR",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}