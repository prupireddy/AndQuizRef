package edu.uchicago.gerber.capsquiz.navigation

 sealed class Screen(val route: String) {
    object Home : Screen(route = "home_screen")
    object Question : Screen(route = "question_screen")
    object Result : Screen(route = "result_screen")

}