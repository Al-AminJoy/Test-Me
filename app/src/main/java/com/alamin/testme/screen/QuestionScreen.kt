package com.alamin.testme.screen

import android.widget.Space
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alamin.testme.model.data.Question
import com.alamin.testme.view_model.QuestionViewModel

@Composable
fun QuestionScreen(navController: NavHostController, questions: List<Question>?) {




    val questionViewModel:QuestionViewModel = hiltViewModel()

    Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = questionViewModel.questionNo.toString(), fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)

    }

}