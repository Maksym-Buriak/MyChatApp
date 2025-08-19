package com.maks_buriak.mychat.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

//        // Якщо вже авторизований → відразу в MainActivity
//        if (currentUser != null) {
//            if (currentUser.phoneNumber != null) {
//                startMain()
//            } else {
//                startPhoneAuth()
//            }
//            return
//        }

        // Якщо користувач вже авторизований
        if (currentUser != null) {
            navigateAfterSignIn(currentUser.phoneNumber)
            return
        }

        setContent {
            MyChatTheme {
                val navigateToPhoneAuth by authViewModel.navigateToPhoneAuth.collectAsState()
                if (navigateToPhoneAuth) {
                    startPhoneAuth()
                }

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

    private fun startPhoneAuth() {
        startActivity(Intent(this, PhoneAuthActivity::class.java))
        finish()
    }

    private fun navigateAfterSignIn(phoneNumber: String?) {
        if (phoneNumber != null) startMain()
        else startPhoneAuth()
    }
}