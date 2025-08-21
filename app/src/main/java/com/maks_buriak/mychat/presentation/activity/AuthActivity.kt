package com.maks_buriak.mychat.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.maks_buriak.mychat.data.authentication.phoneNumber.setCurrentActivity
import com.maks_buriak.mychat.presentation.screen.AuthScreen
import com.maks_buriak.mychat.presentation.viewmodel.AuthViewModel
import com.maks_buriak.mychat.ui.theme.MyChatTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModel<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCurrentActivity(this)

        val currentUser = authViewModel.userState.value

        // Якщо користувач вже авторизований
        if (currentUser != null) {
            navigateAfterSignIn(currentUser.phoneNumber)
            return
        }

        setContent {
            MyChatTheme {

                AuthScreen(
                    activity = this,
                    viewModel = authViewModel,
                    onSignedIn = {
                        val user = authViewModel.userState.value
                        navigateAfterSignIn(user?.phoneNumber)
                    }
                )
            }
        }
    }

    private fun startMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish() // щоб не можна було вернутися назад
    }

    private fun navigateAfterSignIn(phoneNumber: String?) {
        startMain()
    }
}