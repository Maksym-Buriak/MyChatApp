package com.maks_buriak.mychat.presentation.screen

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.maks_buriak.mychat.R
import com.maks_buriak.mychat.presentation.activity.AuthActivity
import com.maks_buriak.mychat.presentation.viewmodel.MessageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageScreen(messageViewModel: MessageViewModel) {
    // UI тут, можна звертатись до viewModel

    val context = LocalContext.current

    var messageText by rememberSaveable { mutableStateOf("") }
    var menuExpandet by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MyChat")},
                actions = {
                    IconButton(onClick = { menuExpandet = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Меню")
                    }

                    DropdownMenu(
                        expanded = menuExpandet,
                        onDismissRequest = { menuExpandet = false }
                    ) {

                        //Верхня частина меню з акаунтом
                        messageViewModel.currentUser?.let { account ->
                            Row(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                //Аватарка
                                AsyncImage(
                                    model = account.photoUrl ?: R.drawable.ic_launcher_background,
                                    contentDescription = "User Avatar",
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape),
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {
                                    Text(
                                        text = account.displayName ?: "Unknown",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Text(
                                        text = account.email ?: "",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }

                            HorizontalDivider()
                        }

                        DropdownMenuItem(
                            //три крапки справа вгорі

                            text = { Text("Вийти з акаунту") },
                            onClick = {
                                menuExpandet = false
                                messageViewModel.signOut()

                                //Переходимо на екран логіну
                                val intent = Intent(context, AuthActivity::class.java)
                                context.startActivity(intent)

                                //Закриваємо поточну Activity
                                if (context is android.app.Activity) {
                                    context.finish()
                                }
                            }
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                TextField(
                    value = messageText,
                    onValueChange = {messageText = it},
                    placeholder = { Text("Введіть повідомлення") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    modifier = Modifier.padding(top = 10.dp),
                    onClick = {
                        messageViewModel.sendMessage(messageText)
                        messageText = ""
                    }
                ) {
                    Text("Відправити на сервер")
                }
            }
        }
    )
}