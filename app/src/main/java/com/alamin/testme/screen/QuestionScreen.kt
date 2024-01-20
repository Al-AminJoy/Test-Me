package com.alamin.testme.screen

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alamin.testme.model.data.Question
import com.alamin.testme.ui.theme.Green
import com.alamin.testme.ui.theme.White
import com.alamin.testme.view_model.QuestionViewModel

private const val TAG = "QuestionScreen"

@Composable
fun QuestionScreen(navController: NavHostController, questions: List<Question>) {


    var isOpenDialog by remember {
        mutableStateOf(false)
    }



    BackHandler {
        isOpenDialog = true
    }

    if (isOpenDialog) {
        AlertDialog(
            title = {
                Text(text = "Warning !")
            }, text = { Text(text = "Do You Want to Cancel Exam ?") },
            onDismissRequest = {
                isOpenDialog = false
            }, confirmButton = {
                Button(onClick = {
                    isOpenDialog = false
                    navController.popBackStack()
                }) {
                    Text(text = "Confirm")
                }

            },
            dismissButton = {
                Button(onClick = {
                    isOpenDialog = false
                }) {
                    Text(text = "Dismiss")
                }
            })
    }


    val questionViewModel: QuestionViewModel = hiltViewModel()
    Log.d(TAG, "QuestionScreen: ")

    var nextQuestionIndex by remember {
        mutableStateOf(questionViewModel.questionNo+1)
    }

    ShowMessage(questionViewModel)

    if (questionViewModel._questionList.isEmpty()) {
        Log.d(TAG, "QuestionScreen: Set")

        questionViewModel.setQuestionList(questions)
    }

    if (questionViewModel._questionList.isNotEmpty()) {
        Log.d(TAG, "QuestionScreen: Render")

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                text = (questionViewModel.questionNo + 1).toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            QuestionCard(questionViewModel)


            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                ElevatedButton(onClick = {
                    questionViewModel.decreaseQuestion()
                }) {
                    Text(text = "Previous")
                }
                ElevatedButton(onClick = {
                    questionViewModel.increaseQuestion()
                    nextQuestionIndex = questionViewModel.questionNo
                }) {
                    Text(text = "Next")
                }
            }


        }
    }


}

@Composable
fun QuestionCard(questionViewModel: QuestionViewModel) {
    ElevatedCard(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = questionViewModel.getQuestion(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(8.dp)

            )
            Spacer(modifier = Modifier.height(8.dp))

            var selectedOption by remember {
                mutableStateOf("")
            }
            val onSelectionChange = { text: String ->
                selectedOption = text
            }

            LazyColumn(contentPadding = PaddingValues(horizontal = 8.dp)) {

                items(questionViewModel.getAnswers()) {

                    Text(
                        text = it,
                        color = White,
                        textAlign = TextAlign.Start,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSelectionChange(it)

                            }
                            .background(
                                color = if (selectedOption == it) Green else MaterialTheme.colorScheme.tertiary,
                                shape = RoundedCornerShape(size = 8.dp)
                            )
                            .padding(8.dp)

                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }

}

@Composable
fun ShowMessage(questionViewModel: QuestionViewModel) {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        questionViewModel.message.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

}
