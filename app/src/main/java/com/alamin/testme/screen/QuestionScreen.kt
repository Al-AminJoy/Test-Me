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
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.window.Dialog
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


    val questionViewModel: QuestionViewModel = hiltViewModel()
    Log.d(TAG, "QuestionScreen: ")



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


    if (questionViewModel.openResultDialog) {
        Dialog(onDismissRequest = {
            questionViewModel.openResultDialog = true
        }) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Column(
                   // horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(8.dp, 20.dp)
                ) {
                    Text(text = "Congratulations !", color = Green, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "You answered ${questionViewModel.correctAnswerCount} correct answer out of 10 Question.",
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row (horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()){
                        TextButton(onClick = {
                            questionViewModel.openResultDialog = false
                            navController.popBackStack()
                        }) {
                            Text(text = "Ok", color = Green)
                        }
                    }
                }
            }
        }
    }



    ShowMessage(questionViewModel)

    if (questionViewModel._questionList.isEmpty()) {
        questionViewModel.setQuestionList(questions)
    }

    if (questionViewModel._questionList.isNotEmpty()) {
        Log.d(TAG, "QuestionScreen: Inside")

        val question = questionViewModel.getQuestion()
        val answers = questionViewModel.getAnswers()



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

            QuestionCard(question, answers) {
                questionViewModel.selectedAnswer = it
            }

            Row(
                horizontalArrangement = if (questionViewModel.questionNo == 9) Arrangement.Center else Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                ElevatedButton(onClick = {
                    questionViewModel.increaseQuestion()
                }) {
                    Text(text = if (questionViewModel.questionNo == 9) "Show Result" else "Next")
                }
            }


        }
    }


}

@Composable
fun QuestionCard(
    question: String,
    answer: List<String>,
    selectedAnswerListener: (String) -> Unit
) {
    ElevatedCard(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = question,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(8.dp)

            )
            Spacer(modifier = Modifier.height(8.dp))
            var selectedOption = remember {
                mutableStateOf("")
            }

            LazyColumn(contentPadding = PaddingValues(horizontal = 8.dp)) {

                items(answer) {

                    Text(
                        text = it,
                        color = White,
                        textAlign = TextAlign.Start,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                //onSelectionChange(it)
                                selectedOption.value = it
                                selectedAnswerListener(it)
                            }
                            .background(
                                color = if (selectedOption.value == it) Green else MaterialTheme.colorScheme.tertiary,
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
