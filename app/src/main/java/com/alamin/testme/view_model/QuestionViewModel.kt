package com.alamin.testme.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor():ViewModel() {
    var questionNo by mutableStateOf(1)

    fun increaseQuestion(){
        questionNo++
    }

}