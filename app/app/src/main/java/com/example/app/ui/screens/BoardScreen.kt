package com.example.app.ui.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.example.app.ui.Destinations
import com.example.app.ui.theme.Purple200
import com.example.app.ui.theme.Purple700
import com.example.app.ui.viewmodels.BoardViewModel

@Composable
fun BoardScreen(
    navController: NavHostController,
    boardViewModel: BoardViewModel,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current as Activity
    val board by boardViewModel.board.collectAsState()
    val isMyTurn by boardViewModel.isMyTurn.collectAsState()

    boardViewModel.message.observe(LocalLifecycleOwner.current) { message ->
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        navController.navigate(Destinations.HOME)
    }

    fun handleMove(position: Int) {
        boardViewModel.move(
            position = position
        )
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),

            ) {
            itemsIndexed(board) { position, value ->
                Button(
                    onClick = { handleMove(position = position) },
                    modifier = Modifier
                        .padding(4.dp)
                        .aspectRatio(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors().copy(
                        contentColor = Purple700,
                        containerColor = Color.White,
                        disabledContentColor = Purple700,
                        disabledContainerColor = Purple200
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp
                    ),
                    enabled = if (value != null) { false } else { isMyTurn }
                ) {
                    Text(text = value ?: "", fontSize = 40.sp)
                }
            }
        }
    }
}
