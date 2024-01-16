package com.alamin.testme.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alamin.testme.model.data.Question
import com.alamin.testme.screen.HomeScreen
import com.alamin.testme.screen.QuestionScreen

private const val TAG = "NavGraph"

@Composable
fun SetupNavGraph( navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(route = Screen.Question.route /*arguments = listOf(
                navArgument("questions"){type = NavType.StringType}
            )*/){

            val questions = navController.previousBackStackEntry?.savedStateHandle?.get<List<Question>?>("questions")
            QuestionScreen(navController,questions)

        }
    }
}