package com.maks_buriak.mychat.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.maks_buriak.mychat.presentation.navigation.AppNavHost
import com.maks_buriak.mychat.ui.theme.MyChatTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyChatTheme {
                AppNavHost(navController = rememberNavController())
            }
        }
    }
}