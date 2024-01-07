package com.alamin.testme.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alamin.testme.model.data.NetworkResponse
import com.alamin.testme.ui.theme.Black
import com.alamin.testme.view_model.HomeViewModel

private const val TAG = "HomeScreen"
@Composable
@Preview
fun HomeScreen() {

    val homeViewModel:HomeViewModel = hiltViewModel()

    val categoryList = homeViewModel.categoryList.collectAsState()
    val difficultyList = homeViewModel.difficultyList.collectAsState()
    val questionTypeList = homeViewModel.questionTypeList.collectAsState()

    val questionResponse = homeViewModel.questionResponse.collectAsState()

    val requested = questionResponse.value

    LaunchedEffect(key1 = requested ){
        Log.d(TAG, "HomeScreen: RECALLED")
        when(questionResponse.value){
            is NetworkResponse.Empty<*> -> {

            }
            is NetworkResponse.Error<*> -> { }
            is NetworkResponse.Loading<*> -> {
                Log.d(TAG, "HomeScreen: Loading")
            }
            is NetworkResponse.Success<*> -> {
                val data = questionResponse.value.data
                Log.d(TAG, "HomeScreen: $data")
            }
        }
    }


  //  Log.d(TAG, "HomeScreen: ${questionResponse.value..questions}")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {


        val selectedCategory = remember {
            mutableStateOf("")
        }

        val selectedDiffficulty = remember {
            mutableStateOf("")
        }

        val selectedType = remember {
            mutableStateOf("")
        }

        Text(
            text = "Welcome \nLet's Start a Test !",
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 16.sp, fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))

        CategoryItems(categoryList.value,selectedCategory.value){
            selectedCategory.value = it
        }
        Spacer(modifier = Modifier.height(20.dp))
        DifficultyItems(difficultyList.value,selectedDiffficulty.value){
            selectedDiffficulty.value = it
        }
        Spacer(modifier = Modifier.height(20.dp))
        QuestionTypeItems(questionTypeList.value,selectedType.value){
            selectedType.value = it
        }
        Spacer(modifier = Modifier.height(20.dp))
        SubmitButton(selectedCategory.value,selectedDiffficulty.value,selectedType.value,homeViewModel)

    }


}

@Composable
fun SubmitButton(
    selectedCategory: String,
    selectedDiffficulty: String,
    selectedType: String,
    homeViewModel: HomeViewModel,
) {
    ElevatedButton(onClick = {
        Log.d(TAG, "SubmitButton: $selectedCategory $selectedDiffficulty $selectedType")
          homeViewModel.requestQuestion(10,12,selectedDiffficulty.lowercase(),selectedType.lowercase())
    }, shape = RoundedCornerShape(8.dp)) {
        Text(text = "Start", color = MaterialTheme.colorScheme.primary)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionTypeItems(questionTypeList: MutableList<String>, selectedType: String,onSelect: (String) -> Unit) {
    val isExpanded = remember {
        mutableStateOf(false)
    }


    ExposedDropdownMenuBox(
        expanded = isExpanded.value,
        onExpandedChange = { newValue -> isExpanded.value = newValue }) {

        TextField(
            value = selectedType, onValueChange = {}, readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = isExpanded.value
                )
            },
            placeholder = { Text(text = "Select Question Type")},
            colors = ExposedDropdownMenuDefaults.textFieldColors(textColor = Black),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = isExpanded.value,
            onDismissRequest = { isExpanded.value = false }) {
            for (type in questionTypeList){
                DropdownMenuItem(text = { Text(text = type) }, onClick = {
                    isExpanded.value = false
                    onSelect(type)
                })
            }
        }


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryItems(categoryList: MutableList<String>, value: String,onSelect: (String) -> Unit) {
    var isExpanded = remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = isExpanded.value, onExpandedChange = { newValue ->
        isExpanded.value = newValue
    }) {
        TextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded.value)
            },
            placeholder = { Text(text = "Select Category") },
            colors = ExposedDropdownMenuDefaults.textFieldColors(textColor = Black),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = isExpanded.value,
            onDismissRequest = { isExpanded.value = false }) {
            for (category in categoryList) {
                DropdownMenuItem(text = { Text(text = category) }, onClick = {
                    onSelect(category)
                    isExpanded.value = false
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DifficultyItems(difficultyList: MutableList<String>, selectedDiffficulty: String,onSelect: (String) -> Unit) {

    val isExpanded = remember {
        mutableStateOf(false)
    }


    ExposedDropdownMenuBox(expanded = isExpanded.value, onExpandedChange = { newValue ->
        isExpanded.value = newValue
    }) {

        TextField(
            value = selectedDiffficulty,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = isExpanded.value
                )
            },
            placeholder = { Text(text = "Select Difficulty") },
            colors = ExposedDropdownMenuDefaults.textFieldColors(textColor = Black),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = isExpanded.value,
            onDismissRequest = { isExpanded.value = false }) {
            for (difficulty in difficultyList) {
                DropdownMenuItem(text = { Text(text = difficulty) }, onClick = {
                    isExpanded.value = false
                    onSelect(difficulty)
                })
            }
        }

    }


}
