package com.alamin.testme.utils

sealed class Screen(val route:String){
    object Home:Screen("home_screen")
    object Question:Screen("question_screen")
}
