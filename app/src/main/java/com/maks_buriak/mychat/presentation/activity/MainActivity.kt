package com.maks_buriak.mychat.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.auth.FirebaseAuth
import com.maks_buriak.mychat.presentation.screen.MessageScreen
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.maks_buriak.mychat.presentation.viewmodel.MessageViewModel
import com.maks_buriak.mychat.ui.theme.MyChatTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MessageViewModel by viewModel<MessageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyChatTheme {

                MessageScreen(viewModel)

            }
        }
    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser

        if (user == null) {
            // користувач не авторизований → назад на екран логіну
            finish()
            return
        }

//        if (user.phoneNumber.isNullOrEmpty()) {
//            startActivity(Intent(this, PhoneAuthActivity::class.java))
//            finish()
//            return
//        }

        user.getIdToken(true).addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                FirebaseAuth.getInstance().signOut()
                finish() // закриваємо Activity або повертаємо на екран логіну
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshCurrentUser()
    }
}