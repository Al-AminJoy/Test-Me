package com.alamin.testme.view_model

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor():ViewModel() {

    val categoryList = MutableStateFlow<MutableList<String>>(arrayListOf("Any", "History"))
    val difficultyList = MutableStateFlow<MutableList<String>>(arrayListOf("Any", "Easy", "Medium", "Hard"))
    val questionTypeList = MutableStateFlow<MutableList<String>>(arrayListOf("Any", "Multiple", "True/False"))


}