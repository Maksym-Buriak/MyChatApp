package com.maks_buriak.mychat.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//
////                    // Write a message to the database
////                    val database = Firebase.database
////                    val myRef = database.getReference("message")
////
////                    myRef.setValue("Hello, World!")
//
//
//                }

                MessageScreen(viewModel)

            }
        }
    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        user?.getIdToken(true)?.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                FirebaseAuth.getInstance().signOut()
                finish() // закриваємо Activity або повертаємо на екран логіну
            }
        }
    }
}

//@Composable
//fun MessageScreen(messageViewModel: MessageViewModel) {
//    // UI тут, можна звертатись до viewModel
//
//    var messageText by rememberSaveable { mutableStateOf("") }
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        TextField(
//            value = messageText,
//            onValueChange = {messageText = it},
//            placeholder = { Text("Введіть повідомлення") }
//
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Button(
//            onClick = {
//            messageViewModel.sendMessage(messageText)
//            messageText = ""
//            }
//        ) {
//          Text("Відправити на сервер")
//        }
//    }
//}