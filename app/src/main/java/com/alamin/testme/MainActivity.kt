package com.alamin.testme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.alamin.testme.ui.theme.Pink40
import com.alamin.testme.ui.theme.TestMeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestMeTheme {
                Scaffold(topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Green, titleContentColor = Color.White),
                        title = { Text(text = "Test Me") },

                        )
                }) {
                    Box(modifier = Modifier.padding(it)) {
                        App()
                    }
                }
            }
        }
    }

    @Composable
    @Preview
    fun App() {

    }
}

