package com.example.anite_mob

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.anite_mob.screens.CadastroScreen
import com.example.anite_mob.screens.HomeScreen
import com.example.anite_mob.screens.LoginScreen
import com.example.anite_mob.screens.EsqueciSenhaScreen
import com.example.anite_mob.screens.DashboardScreen
import com.example.anite_mob.ui.theme.AniMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
                            LoginScreen(
                                onBack = { navController.popBackStack() },
                                onNavigateToEsqueciSenha = { navController.navigate("esqueci_senha") },
                                onLoginSuccess = { 
                                    navController.navigate("dashboard") {
                                        popUpTo("home") { inclusive = false }
                                    }
                                }
                            )
                        }
                        composable("cadastro") {
                            CadastroScreen(
                                onBack = { navController.popBackStack() },
                                onCadastroSuccess = {
                                    navController.navigate("login")
                                }
                            )
                        }
                        composable("esqueci_senha") {
                            EsqueciSenhaScreen(onBack = { navController.popBackStack() })
                        }
                        composable("dashboard") {
                            DashboardScreen(
                                onLogout = { 
                                    navController.navigate("home") {
                                        popUpTo("home") { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
