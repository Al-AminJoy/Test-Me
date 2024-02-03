package com.alamin.testme.view_model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.testme.model.data.Question
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

private const val TAG = "QuestionViewModel"

@HiltViewModel
class QuestionViewModel @Inject constructor() : ViewModel() {
    var questionNo by mutableIntStateOf(0)
    val questionList = mutableStateListOf<Question>()
    val message = MutableSharedFlow<String>()
    var selectedAnswer by
        mutableStateOf("")

    var openResultDialog by
        mutableStateOf(false)

    var correctAnswerCount by mutableIntStateOf(0)



    fun increaseQuestion() {
        Log.d(TAG, "increaseQuestion: ${questionList.size} $questionNo")
        viewModelScope.launch {
        if(selectedAnswer.isEmpty()){
            message.emit("Select Answer First")
        }else if (questionList.size - 1 > questionNo) {
            if (selectedAnswer.trim().equals(getCorrectAnswer())){
                correctAnswerCount++
            }
            getQuestion().answered = selectedAnswer
            questionNo++
            selectedAnswer = ""
        } else {
            if (selectedAnswer.trim().equals(getCorrectAnswer())){
                correctAnswerCount++
            }
            getQuestion().answered = selectedAnswer
            openResultDialog = true
            Log.d(TAG, "increaseQuestion: ${questionList.toList()}")
            }
        }
    }

    fun decreaseQuestion() {
        if (questionNo > 0) {
            questionNo--
        } else {
            viewModelScope.launch {
                message.emit("Out of Index")
            }
        }
    }

    fun setQuestionList(questionList: List<Question>) {
        this.questionList.addAll(questionList)
    }

    fun getQuestion(): Question {
        return questionList[questionNo]
    }

    private fun getCorrectAnswer(): String {
        val question = questionList[questionNo]
        return question.correctAnswer
    }

    fun getAnswers(): List<String> {
        val question = questionList[questionNo]
        val questionSet = arrayListOf<String>()
        questionSet.addAll(question.incorrectAnswers)
        questionSet.add(question.correctAnswer)
        return questionSet.shuffled()
    }

}