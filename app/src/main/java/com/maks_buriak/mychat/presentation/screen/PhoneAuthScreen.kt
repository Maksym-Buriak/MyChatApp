package com.maks_buriak.mychat.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maks_buriak.mychat.presentation.viewmodel.PhoneAuthViewModel

@Composable
fun PhoneAuthScreen(
    viewModel: PhoneAuthViewModel,
    onVerified: () -> Unit
) {
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var code by rememberSaveable { mutableStateOf("") }
    var verificationSent by rememberSaveable { mutableStateOf(false) }

    val status by viewModel.status.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Підтвердження номеру телефону", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        if (!verificationSent) {
            TextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Номер телефону") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.sendCode(phoneNumber)
                    verificationSent = true
                }
            ) {
                Text("Надіслати код")
            }
        } else {

            TextField(
                value = code,
                onValueChange = { code = it },
                label = { Text("Введіть код") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.verifyCode(code, phoneNumber, onVerified)
                }
            ) {
                Text("Підтвердити код")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        status?.let { Text(it, color = MaterialTheme.colorScheme.primary) }
    }
}