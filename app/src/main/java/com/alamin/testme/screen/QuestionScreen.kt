package com.alamin.testme.screen

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alamin.testme.R
import com.alamin.testme.model.data.Question
import com.alamin.testme.ui.theme.White
import com.alamin.testme.view_model.QuestionViewModel

private const val TAG = "QuestionScreen"
@Composable
fun QuestionScreen(navController: NavHostController, questions: MutableList<Question>) {

    val questionViewModel: QuestionViewModel = hiltViewModel()
    Log.d(TAG, "QuestionScreen: ")

    questionViewModel.setQuestionList(questions)


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

                LazyColumn(contentPadding = PaddingValues(horizontal = 8.dp)) {

                    items(questionViewModel.getAnswers()) {
                        Text(
                            text = it,
                            color = White,
                            textAlign = TextAlign.Start,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    MaterialTheme.colorScheme.tertiary,
                                    shape = RoundedCornerShape(size = 8.dp)
                                )
                                .padding(8.dp)

                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }

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
            }) {
                Text(text = "Next")
            }
        }


    }


}