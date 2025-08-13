package com.maks_buriak.mychat.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.maks_buriak.mychat.presentation.screen.AuthScreen
import com.maks_buriak.mychat.presentation.viewmodel.AuthViewModel
import com.maks_buriak.mychat.ui.theme.MyChatTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModel<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Якщо вже авторизований → відразу в MainActivity
        if (authViewModel.userState.value != null) {
            startMain()
            return
        }

        setContent {
            MyChatTheme {
                AuthScreen(
                    activity = this,
                    viewModel = authViewModel,
                    onSignedIn = { startMain() }
                )
            }
        }
    }

    private fun startMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish() // щоб не можна було вернутися назад
    }
}