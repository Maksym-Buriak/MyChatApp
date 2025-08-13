package com.maks_buriak.mychat.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maks_buriak.mychat.presentation.viewmodel.MessageViewModel

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