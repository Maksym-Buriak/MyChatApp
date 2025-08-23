//package com.maks_buriak.mychat.presentation.activity
//
//import android.content.Intent
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.navigation.compose.rememberNavController
//import com.maks_buriak.mychat.data.authentication.phoneNumber.setCurrentActivity
//import com.maks_buriak.mychat.presentation.navigation.AppNavHost
//import com.maks_buriak.mychat.presentation.screen.AuthScreen
//import com.maks_buriak.mychat.presentation.viewmodel.AuthViewModel
//import com.maks_buriak.mychat.ui.theme.MyChatTheme
//import org.koin.androidx.viewmodel.ext.android.viewModel
//
//class AuthActivity : ComponentActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContent {
//            MyChatTheme {
//                AppNavHost(navController = rememberNavController())
//            }
//        }
//    }
//}