package com.macaosoftware.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.macaosoftware.ui.ui.DayScheduleApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            DayScheduleApp()
        }
    }
}

@Preview
@Composable
fun DayScheduleAppAndroidPreview() {
    DayScheduleApp()
}