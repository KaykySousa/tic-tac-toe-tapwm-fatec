package com.example.app.ui

import android.net.wifi.hotspot2.pps.HomeSp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.app.ui.screens.AuthScreen
import com.example.app.ui.screens.BoardScreen
import com.example.app.ui.screens.HomeScreen
import com.example.app.ui.viewmodels.AuthViewModel
import com.example.app.ui.viewmodels.BoardViewModel
import com.example.app.ui.viewmodels.HomeViewModel

object Destinations {
    const val HOME = "home"
    const val AUTH = "auth"
    const val BOARD = "board/{gameId}"

    fun BOARD(gameId: String): String {
        return "board/${gameId}"
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier,
    authViewModel: AuthViewModel = viewModel<AuthViewModel>(),
    homeViewModel: HomeViewModel = viewModel<HomeViewModel>()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = Destinations.HOME
        ) {
            HomeScreen(
                navController = navController,
                homeViewModel = homeViewModel
            )
        }

        composable(
            route = Destinations.AUTH
        ) {
            AuthScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(
            route = Destinations.BOARD,
            arguments = listOf(navArgument("gameId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            BoardScreen(
                navController = navController,
                boardViewModel = viewModel<BoardViewModel>(factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return BoardViewModel(
                            gameId = backStackEntry.arguments?.getString("gameId")!!
                        ) as T
                    }
                })
            )
        }
    }
}

