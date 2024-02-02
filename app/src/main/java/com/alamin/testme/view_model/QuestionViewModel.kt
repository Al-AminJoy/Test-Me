package com.alamin.testme.view_model

import android.util.Log
import androidx.compose.runtime.getValue
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
    var questionNo by mutableStateOf(0)
    val _questionList = mutableStateListOf<Question>()
    val message = MutableSharedFlow<String>()
    var selectedAnswer by
        mutableStateOf("")


    fun increaseQuestion() {
        Log.d(TAG, "increaseQuestion: ${_questionList.size} $questionNo")
        viewModelScope.launch {
        if(selectedAnswer.isEmpty()){
            message.emit("Select Answer First")
        }else if (_questionList.size - 1 > questionNo) {
            questionNo++
            selectedAnswer = ""
        } else {
                message.emit("Out of Index")
            }
        }
    }

    fun decreaseQuestion() {
        Log.d(TAG, "decreaseQuestion: ${_questionList.size} $questionNo")

        if (questionNo > 0) {
            questionNo--
        } else {
            viewModelScope.launch {
                message.emit("Out of Index")
            }
        }

    }

    fun setQuestionList(questionList: List<Question>) {
        _questionList.addAll(questionList)
    }

    fun getQuestion(): String {
        return _questionList[questionNo].question
    }

    fun getCorrectAnswer(): String {
        val question = _questionList[questionNo]
        return question.correctAnswer
    }

    fun getAnswers(): List<String> {
        val question = _questionList[questionNo]
        val questionSet = arrayListOf<String>()
        questionSet.addAll(question.incorrectAnswers)
        questionSet.add(question.correctAnswer)
        return questionSet.shuffled()
    }

}