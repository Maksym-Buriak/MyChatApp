package com.maks_buriak.mychat.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import com.maks_buriak.mychat.presentation.screen.AuthScreen
import com.maks_buriak.mychat.presentation.screen.MessageScreen
import com.maks_buriak.mychat.presentation.screen.NickNameScreen
import com.maks_buriak.mychat.presentation.screen.PhoneAuthScreen
import com.maks_buriak.mychat.presentation.screen.SplashScreen
import com.maks_buriak.mychat.presentation.viewmodel.AuthViewModel
import com.maks_buriak.mychat.presentation.viewmodel.MessageViewModel
import com.maks_buriak.mychat.presentation.viewmodel.NickNameViewModel
import com.maks_buriak.mychat.presentation.viewmodel.PhoneAuthViewModel
import org.koin.androidx.compose.koinViewModel

sealed class Screen(val route: String) {
    object Auth : Screen("auth")
    object PhoneAuth : Screen("phone_auth")
    object Messages : Screen("messages")
    object NickName : Screen("nick_name")
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AppNavHost(navController: NavHostController) {


    val authViewModel: AuthViewModel = koinViewModel()
    val userState by authViewModel.userState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = when {
            userState == null -> Screen.Auth.route
            userState?.nickName.isNullOrEmpty() -> Screen.NickName.route
            else -> Screen.Messages.route
        }
    ) {

        composable(Screen.NickName.route) {
            val viewModel: NickNameViewModel = koinViewModel()
            val uid = userState?.uid ?: return@composable

            NickNameScreen(
                uid = uid,
                viewModel = viewModel,
                onNickSaved = {
                    navController.navigate(Screen.Messages.route) {
                        popUpTo(Screen.NickName.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Auth.route) {
            AuthScreen(
                viewModel = authViewModel,
                onSignedIn = {
                    val user = userState
                    if (user?.nickName.isNullOrEmpty()) {
                        navController.navigate(Screen.NickName.route) {
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
                onAddNick = {
                    navController.navigate(Screen.NickName.route)
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