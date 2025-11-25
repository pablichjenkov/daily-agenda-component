package com.macaosoftware.ui

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.macaosoftware.ui.ui.DayScheduleApp

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        DayScheduleApp()
    }
}