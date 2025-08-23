//package com.maks_buriak.mychat.presentation.activity
//
//import android.content.Intent
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import com.maks_buriak.mychat.data.authentication.phoneNumber.setCurrentActivity
//import com.maks_buriak.mychat.presentation.screen.PhoneAuthScreen
//import com.maks_buriak.mychat.presentation.viewmodel.PhoneAuthViewModel
//import com.maks_buriak.mychat.ui.theme.MyChatTheme
//import org.koin.androidx.viewmodel.ext.android.viewModel
//
//class PhoneAuthActivity : ComponentActivity() {
//
//    private val phoneAuthViewModel: PhoneAuthViewModel by viewModel<PhoneAuthViewModel>()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setCurrentActivity(this)
//
//        setContent {
//            MyChatTheme {
//                PhoneAuthScreen(
//                    viewModel = phoneAuthViewModel,
//                    onVerified = { startMain() }
//                )
//            }
//        }
//    }
//
//    private fun startMain() {
//        startActivity(Intent(this, MainActivity::class.java))
//        finish()
//    }
//}