package com.alamin.testme.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.testme.model.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeViewModel"
@HiltViewModel
class HomeViewModel @Inject constructor(private val questionRepository: QuestionRepository) :
    ViewModel() {


    val categoryList = MutableStateFlow<MutableList<String>>(arrayListOf("Any", "History"))
    val difficultyList =
        MutableStateFlow<MutableList<String>>(arrayListOf("Any", "Easy", "Medium", "Hard"))
    val questionTypeList =
        MutableStateFlow<MutableList<String>>(arrayListOf("Any", "Multiple", "True/False"))


    val questionResponse = questionRepository.questionResponse
    fun requestQuestion(amount:Int,
                        categoryId:Int,
                        difficulty:String,
                        type:String) {

        viewModelScope.launch {
            delay(2000)
            questionRepository.requestQuestion(amount,categoryId,difficulty,type)
        }

    }

}