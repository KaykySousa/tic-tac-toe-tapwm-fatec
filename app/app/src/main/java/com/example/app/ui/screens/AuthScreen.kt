package com.example.app.ui.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.app.ui.Destinations
import com.example.app.ui.theme.Neutral300
import com.example.app.ui.theme.Neutral800
import com.example.app.ui.theme.Purple700
import com.example.app.ui.viewmodels.AuthViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current as Activity
    val coroutineScope = rememberCoroutineScope()

    fun handleClick() {
        coroutineScope.launch {
            authViewModel.googleSignIn(context).collect { result ->
                result.fold(
                    onSuccess = { authResult ->
                        navController.navigate(Destinations.HOME)
                    },
                    onFailure = { exception ->
                        val toast: Toast = Toast.makeText(context, exception.message, Toast.LENGTH_SHORT)
                        toast.show()
                    }
                )
            }
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Purple700)
            .padding(20.dp)
            .then(modifier),
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Entre e prove suas habilidades no clássico jogo da velha!",
                color = Purple700,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
            Button(
                onClick = { handleClick() },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors().copy(
                    contentColor = Neutral800,
                    containerColor = Color.White
                ),
                border = BorderStroke(
                    color = Neutral300,
                    width = 1.dp
                )

            ) {
                Text(text = "Entre com o Google")
            }
        }
    }
}
