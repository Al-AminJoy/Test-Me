package com.alamin.testme.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alamin.testme.model.data.NetworkResponse
import com.alamin.testme.ui.theme.Black
import com.alamin.testme.utils.Screen
import com.alamin.testme.view_model.HomeViewModel
import com.google.gson.Gson
import kotlin.math.log

private const val TAG = "HomeScreen"

@Composable
fun HomeScreen(navController: NavHostController) {

    val homeViewModel: HomeViewModel = hiltViewModel()

    val categoryList = homeViewModel.categoryList.collectAsState()
    val difficultyList = homeViewModel.difficultyList.collectAsState()
    val questionTypeList = homeViewModel.questionTypeList.collectAsState()

    /*    val categoryList = arrayListOf("Any", "History")
        val difficultyList = arrayListOf("Any", "Easy", "Medium", "Hard")
        val questionTypeList = arrayListOf("Any", "Multiple", "True/False")*/


    var showLoading by remember {
        mutableStateOf(false)
    }

    ShowToastMessage(homeViewModel)

    if (showLoading) {
        Loader()
    }

    LaunchedEffect(key1 = Unit) {
        homeViewModel.questionResponse.collect{
            when (it) {
                is NetworkResponse.Empty -> {

                }

                is NetworkResponse.Error -> {
                    showLoading = false
                }

                is NetworkResponse.Loading -> {
                    showLoading = true
                }

                is NetworkResponse.Success -> {
                    val data = it.data
                    showLoading = false
                    val json = Gson().toJson(data).toString()
                    Log.d(TAG, "HomeScreen: $json")
                    navController.currentBackStackEntry?.savedStateHandle?.set(key = "questions",value = data)
                    navController.navigate(route = Screen.Question.route)/*{
                        popUpTo(Screen.Home.route)
                    }*/
                }
            }
        }
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {


        Text(
            text = "Welcome \nLet's Start a Test !",
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 16.sp, fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        CategoryItems(categoryList.value, homeViewModel)
        Spacer(modifier = Modifier.height(20.dp))
        DifficultyItems(difficultyList.value, homeViewModel)
        Spacer(modifier = Modifier.height(20.dp))
        QuestionTypeItems(questionTypeList.value, homeViewModel)
        Spacer(modifier = Modifier.height(20.dp))
        SubmitButton(
            homeViewModel
        )
    }
}

@Composable
fun Loader() {
    Box(contentAlignment = Alignment.Center,modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(strokeWidth = 4.dp, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun ShowToastMessage(homeViewModel: HomeViewModel) {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        homeViewModel.message.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun SubmitButton(
    homeViewModel: HomeViewModel
) {
    ElevatedButton(onClick = {
        homeViewModel.requestQuestion()
    }, shape = RoundedCornerShape(8.dp)) {
        Text(text = "Start", color = MaterialTheme.colorScheme.primary)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionTypeItems(
    questionTypeList: MutableList<String>,
    homeViewModel: HomeViewModel
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded }) {

        OutlinedTextField(
            value = homeViewModel.questionType, onValueChange = {
                homeViewModel.questionType = it
            }, readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = isExpanded
                )
            },
            placeholder = { Text(text = "Select Question Type") },
            colors = ExposedDropdownMenuDefaults.textFieldColors(textColor = Black),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }) {
            for (type in questionTypeList) {
                DropdownMenuItem(text = { Text(text = type) }, onClick = {
                    isExpanded = false
                    homeViewModel.questionType = type
                })
            }
        }


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryItems(categoryList: MutableList<String>, homeViewModel: HomeViewModel) {
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = {
        isExpanded = !isExpanded
    }) {
        OutlinedTextField(
            value = homeViewModel.category,
            onValueChange = { category ->
                homeViewModel.category = category
            },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            placeholder = { Text(text = "Select Category") },
            colors = ExposedDropdownMenuDefaults.textFieldColors(textColor = Black),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }) {
            for (category in categoryList) {
                DropdownMenuItem(text = { Text(text = category) }, onClick = {
                    homeViewModel.category = category
                    isExpanded = false
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DifficultyItems(
    difficultyList: MutableList<String>,
    homeViewModel: HomeViewModel
) {

    var isExpanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = {
        isExpanded = !isExpanded
    }) {

        OutlinedTextField(
            value = homeViewModel.difficulty,
            onValueChange = {
                homeViewModel.difficulty = it
            },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = isExpanded
                )
            },
            placeholder = { Text(text = "Select Difficulty") },
            colors = ExposedDropdownMenuDefaults.textFieldColors(textColor = Black),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }) {
            difficultyList.forEach { difficulty ->
                DropdownMenuItem(text = { Text(text = difficulty) }, onClick = {
                    isExpanded = false
                    homeViewModel.difficulty = difficulty
                })
            }

        }

    }


}
