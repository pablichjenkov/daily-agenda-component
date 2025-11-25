package com.macaosoftware.ui

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.macaosoftware.ui.ui.DayScheduleApp

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "DailyAgendaView",
    ) {
        DayScheduleApp()
    }
}