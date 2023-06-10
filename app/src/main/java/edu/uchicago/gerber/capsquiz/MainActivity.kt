package edu.uchicago.gerber.capsquiz

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import edu.uchicago.gerber.capsquiz.navigation.NavigationGraph
import edu.uchicago.gerber.capsquiz.screens.HomeScreen
import edu.uchicago.gerber.capsquiz.viewmodel.QuizViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = MaterialTheme.colors.background) {
                val navController = rememberNavController()
                NavigationGraph(navController = navController)
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    //see individual screens for preview
}