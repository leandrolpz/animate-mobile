package com.example.animate.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animate.R
import com.example.animate.components.AnimateButton
import com.example.animate.ui.theme.*

@Composable
fun HomeScreen(onNavigateToLogin: () -> Unit, onNavigateToRegister: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Logo superior
        // Nota: Você precisará adicionar o arquivo logo.png/svg em res/drawable
        Box(modifier = Modifier.padding(top = 40.dp).size(100.dp)) {
            // Placeholder para a logo
        }

        // Jumbotron (Card Central)
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(400.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "AniMate",
                    fontSize = 45.sp,
                    fontWeight = FontWeight.Black,
                    color = PurplePrimary
                )
                
                Spacer(modifier = Modifier.height(40.dp))

                AnimateButton(
                    text = "Cadastrar",
                    gradient = RegisterGradient,
                    borderColor = RegisterBorder,
                    textColor = RegisterText,
                    onClick = onNavigateToRegister
                )

                Spacer(modifier = Modifier.height(12.dp))

                AnimateButton(
                    text = "Entrar",
                    gradient = LoginGradient,
                    borderColor = LoginBorder,
                    textColor = LoginText,
                    onClick = onNavigateToLogin
                )
            }
        }

        // Rodapé
        Box(modifier = Modifier.padding(bottom = 20.dp).size(40.dp)) {
            // Placeholder para a loguinho
        }
    }
}
