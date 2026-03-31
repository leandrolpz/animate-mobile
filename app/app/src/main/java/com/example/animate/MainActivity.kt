package com.example.animate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.animate.screens.CadastroScreen
import com.example.animate.screens.HomeScreen
import com.example.animate.screens.LoginScreen
import com.example.animate.ui.theme.AniMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AniMateTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomeScreen(
                                onNavigateToLogin = { navController.navigate("login") },
                                onNavigateToRegister = { navController.navigate("cadastro") }
                            )
                        }
                        composable("login") {
                            LoginScreen(onBack = { navController.popBackStack() })
                        }
                        composable("cadastro") {
                            CadastroScreen(onBack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}
