package com.alamin.testme.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alamin.testme.ui.theme.Black
import com.alamin.testme.view_model.HomeViewModel

@Composable
@Preview
fun HomeScreen() {

    val homeViewModel:HomeViewModel = hiltViewModel()

    val categoryList = homeViewModel.categoryList.collectAsState()
    val difficultyList = homeViewModel.difficultyList.collectAsState()
    val questionTypeList = homeViewModel.questionTypeList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        Text(
            text = "Welcome \nLet's Start a Test !",
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 16.sp, fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))

        CategoryItems(categoryList.value)
        Spacer(modifier = Modifier.height(20.dp))
        DifficultyItems(difficultyList.value)
        Spacer(modifier = Modifier.height(20.dp))
        QuestionTypeItems(questionTypeList.value)

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionTypeItems(questionTypeList:MutableList<String>) {
    val isExpanded = remember {
        mutableStateOf(false)
    }
    val selectedType = remember {
        mutableStateOf("")
    }

    ExposedDropdownMenuBox(
        expanded = isExpanded.value,
        onExpandedChange = { newValue -> isExpanded.value = newValue }) {

        TextField(
            value = selectedType.value, onValueChange = {}, readOnly = true,
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
                    selectedType.value = type
                })
            }
        }


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryItems(categoryList: MutableList<String>) {
    var isExpanded = remember { mutableStateOf(false) }
    val selectedCategory = remember {
        mutableStateOf("")
    }
    ExposedDropdownMenuBox(expanded = isExpanded.value, onExpandedChange = { newValue ->
        isExpanded.value = newValue
    }) {
        TextField(
            value = selectedCategory.value,
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
                    selectedCategory.value = category
                    isExpanded.value = false
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DifficultyItems(difficultyList:MutableList<String>) {

    val isExpanded = remember {
        mutableStateOf(false)
    }

    val selectedDiffficulty = remember {
        mutableStateOf("")
    }

    ExposedDropdownMenuBox(expanded = isExpanded.value, onExpandedChange = { newValue ->
        isExpanded.value = newValue
    }) {

        TextField(
            value = selectedDiffficulty.value,
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
                    selectedDiffficulty.value = difficulty
                })
            }
        }

    }


}
