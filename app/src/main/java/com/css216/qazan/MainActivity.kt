package com.css216.qazan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.css216.qazan.ui.QazanApp
import com.css216.qazan.ui.theme.QazanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val preference by (application as QazanApplication).container
                .userPreferencesRepository.darkTheme.collectAsState(initial = null)
            QazanTheme(darkTheme = preference ?: isSystemInDarkTheme()) {
                QazanApp()
            }
        }
    }
}
