package com.maks_buriak.mychat.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.google.firebase.Firebase
import com.google.firebase.database.database
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
}

@Composable
fun MessageScreen(messageViewModel: MessageViewModel) {
    // UI тут, можна звертатись до viewModel

    var messageText by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = messageText,
            onValueChange = {messageText = it},
            placeholder = { Text("Введіть повідомлення") }

        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
            messageViewModel.sendMessage(messageText)
            messageText = ""
            }
        ) {
          Text("Відправити на сервер")
        }
    }
}