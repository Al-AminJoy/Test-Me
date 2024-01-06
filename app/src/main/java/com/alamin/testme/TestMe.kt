package com.alamin.testme

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TestMe:Application() {
    override fun onCreate() {
        super.onCreate()
    }
}