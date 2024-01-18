package com.alamin.testme.utils

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

            var valueUpdated by remember {
                mutableStateOf(false)
            }
            val questions = navController.previousBackStackEntry?.savedStateHandle?.get<List<Question>?>("questions")

            LaunchedEffect(key1 = Unit){
               Log.d(TAG, "SetupNavGraph: ")
               valueUpdated = true
           }
            if (valueUpdated){
                QuestionScreen(navController,questions as MutableList<Question>)
            }

        }
    }
}