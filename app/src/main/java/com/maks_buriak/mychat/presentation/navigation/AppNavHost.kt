package com.maks_buriak.mychat.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import com.maks_buriak.mychat.presentation.screen.AuthScreen
import com.maks_buriak.mychat.presentation.screen.MessageScreen
import com.maks_buriak.mychat.presentation.screen.PhoneAuthScreen
import com.maks_buriak.mychat.presentation.viewmodel.AuthViewModel
import com.maks_buriak.mychat.presentation.viewmodel.MessageViewModel
import com.maks_buriak.mychat.presentation.viewmodel.PhoneAuthViewModel
import org.koin.androidx.compose.koinViewModel

sealed class Screen(val route: String) {
    object Auth : Screen("auth")
    object PhoneAuth : Screen("phone_auth")
    object Messages : Screen("messages")
}

@Composable
fun AppNavHost(navController: NavHostController) {


    val authViewModel: AuthViewModel = koinViewModel()
    val userState by authViewModel.userState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = if (userState == null) Screen.Auth.route else Screen.Messages.route
    ) {
        composable(Screen.Auth.route) {
            //val authViewModel: AuthViewModel = koinViewModel()
            AuthScreen(
                viewModel = authViewModel,
                onSignedIn = { user ->
                    if (user.phoneNumber.isNullOrEmpty()) {
                        navController.navigate(Screen.Messages.route) {
                            popUpTo(Screen.Auth.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Screen.Messages.route) {
                            popUpTo(Screen.Auth.route) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(Screen.Messages.route) {
            val messageViewModel: MessageViewModel = koinViewModel()
            MessageScreen(
                messageViewModel = messageViewModel,
                onAddPhone = {
                    navController.navigate(Screen.PhoneAuth.route)
                },
                onSignOut = {
                    messageViewModel.signOut() // userManager.logout()
                }
            )
        }

        composable(Screen.PhoneAuth.route) {
            val phoneAuthViewModel: PhoneAuthViewModel = koinViewModel()

            PhoneAuthScreen(
                viewModel = phoneAuthViewModel,
                onVerified = {
                    navController.popBackStack() // повертаємось у Messages
                }
            )
        }
    }
}