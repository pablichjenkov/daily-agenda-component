package com.macaosoftware.ui.dailyagenda

import kotlinx.datetime.LocalTime

class LocalTimeEvent(
    val startSlot: Slot,
    val title: String,
    val startTime: LocalTime,
    val endTime: LocalTime
)

class LocalTimeSlot(
    val title: String,
    val time: LocalTime,
)

