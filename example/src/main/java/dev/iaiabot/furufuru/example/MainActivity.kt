package dev.iaiabot.furufuru.example

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.iaiabot.furufuru.example.first.FirstContent
import dev.iaiabot.furufuru.example.second.SecondContent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "first") {
                composable("first") {
                    FirstContent(navController)
                }
                composable("second") {
                    SecondContent(navController)
                }
            }
        }
    }
}
