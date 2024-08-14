package com.example.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.test.ui.navigation.MainNavHost
import com.example.test.ui.theme.TestStarWarsAppTheme

// TODO: move strings to resources

private val swGradientBrush: Brush get() = Brush.linearGradient(
    listOf(
        Color(0xFF1E1266),
        Color(0xFF000000),
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            TestStarWarsAppTheme {
                MainNavHost(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = swGradientBrush),
                    navController = navController,
                )
            }
        }
    }
}
