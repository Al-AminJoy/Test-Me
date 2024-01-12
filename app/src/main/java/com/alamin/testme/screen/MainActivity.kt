package com.alamin.testme.screen

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contrast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alamin.testme.model.data.Question
import com.alamin.testme.ui.theme.TestMeTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "TestMeMainActivity"
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var themeMode = remember { mutableStateOf(false) }

            TestMeTheme(darkTheme = themeMode.value) {
                Scaffold(topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = Color.White
                        ),
                        title = {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = "Test Me")
                                IconButton(onClick = {
                                    themeMode.value = !themeMode.value
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Contrast,
                                        contentDescription = "Theme Changer"
                                    )
                                }
                            }
                        },

                        )
                }) {
                    Box(modifier = Modifier.padding(it)) {
                        App()
                    }
                }
            }
        }
    }

    @Composable
    @Preview
    fun App() {

        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "home") {
            composable(route = "home") {
                HomeScreen(navController)
            }
            composable(route = "question" /*arguments = listOf(
                navArgument("questions"){type = NavType.StringType}
            )*/){

               LaunchedEffect(key1 = it){
                   val result = navController.previousBackStackEntry?.savedStateHandle?.get<List<Question>?>("questions")
                   Log.d(TAG, "App: $result")
               }

            }
        }


    }

}

