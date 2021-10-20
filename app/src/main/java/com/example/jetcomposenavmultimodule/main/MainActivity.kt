package com.example.jetcomposenavmultimodule.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.jetcomposenavmultimodule.ui.theme.JetComposeNavMultimoduleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            JetComposeNavMultimoduleTheme {
                AppContent()
            }
        }
    }
}