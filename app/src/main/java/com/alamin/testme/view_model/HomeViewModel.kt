package com.alamin.testme.view_model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.testme.model.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
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

    val message = MutableSharedFlow<String>()

    var category by mutableStateOf("")
    var difficulty by mutableStateOf("")
    var questionType by mutableStateOf("")


    val questionResponse = questionRepository.questionResponse
    fun requestQuestion() {
        viewModelScope.launch {
            Log.d(TAG, "requestQuestion: $category $difficulty $questionType")
            if (category.trim().isEmpty()) {
                message.emit("Select Category")
            } else if (difficulty.trim().isEmpty()) {
                message.emit("Select Difficulty")
            } else if (questionType.trim().isEmpty()) {
                message.emit("Select Question Type")
            } else {

                val difficulty = if (difficulty.equals("any",ignoreCase = true)) "" else difficulty

                val questionType = if (questionType.equals(
                        "any",
                        ignoreCase = true
                    )
                ) {
                    ""
                } else if (questionType.equals(
                        "multiple",
                        ignoreCase = true
                    )
                ) {
                    questionType
                } else "boolean"


                questionRepository.requestQuestion(
                    2,
                    12,
                    difficulty.lowercase(),
                    questionType.lowercase()
                )
            }

        }

    }

}