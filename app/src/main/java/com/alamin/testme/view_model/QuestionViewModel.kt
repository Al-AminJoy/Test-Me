package com.alamin.testme.view_model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.alamin.testme.model.data.Question
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "QuestionViewModel"
@HiltViewModel
class QuestionViewModel @Inject constructor():ViewModel() {
    var questionNo by mutableStateOf(0)
    private val _questionList = mutableStateListOf<Question>()

    fun increaseQuestion(){
        questionNo++
    }

    fun decreaseQuestion(){
        questionNo--
    }

    fun setQuestionList(questionList:MutableList<Question>){
        _questionList.addAll(questionList)
    }

    fun getQuestion():String{
        Log.d(TAG, "getQuestion: ")
        return _questionList[questionNo].question
    }

    fun getAnswers():List<String>{
        val question = _questionList[questionNo]
        val questionSet = arrayListOf<String>()
        questionSet.addAll(question.incorrectAnswers)
        questionSet.add(question.correctAnswer)
        return questionSet
    }

}