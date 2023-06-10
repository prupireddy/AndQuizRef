package edu.uchicago.gerber.capsquiz.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.uchicago.gerber.capsquiz.viewmodel.QuizViewModel
import edu.uchicago.gerber.capsquiz.screens.HomeScreen
import edu.uchicago.gerber.capsquiz.screens.QuestionScreen
import edu.uchicago.gerber.capsquiz.screens.ResultScreen


@Composable
fun NavigationGraph(
    navController: NavHostController,
    viewModel: QuizViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController, viewModel)
        }
        composable(route = Screen.Question.route) {
            QuestionScreen(navController, viewModel)
        }
        composable(route = Screen.Result.route) {
            ResultScreen(navController, viewModel)
        }
    }

}