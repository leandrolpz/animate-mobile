package com.example.animate.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animate.components.AnimateButton
import com.example.animate.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EsqueciSenhaScreen(onBack: () -> Unit) {
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = LoginText)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Recuperar Senha",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = PurplePrimary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Insira seu e-mail para receber um link de recuperação.",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = PurplePrimary,
                focusedLabelColor = PurplePrimary
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            AnimateButton(
                text = "Enviar Link",
                gradient = LoginGradient,
                borderColor = LoginBorder,
                textColor = LoginText,
                onClick = { /* Simulação de envio */ }
            )
        }
    }
}
