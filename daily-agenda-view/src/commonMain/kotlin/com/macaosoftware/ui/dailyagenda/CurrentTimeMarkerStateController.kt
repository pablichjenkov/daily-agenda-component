package com.macaosoftware.ui.dailyagenda

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class  CurrentTimeMarkerState(val offsetY: Dp)

internal  class CurrentTimeMarkerStateController(
    val dailyAgendaState: DailyAgendaState
) {

    val state = mutableStateOf(value = CurrentTimeMarkerState(offsetY = 0.dp))

    init {
        val localTime = getCurrentLocalTime()
        val hourOffsetY = (localTime.hour - dailyAgendaState.config.initialSlotValue) * (2 * dailyAgendaState.config.slotHeight)

        val minuteRatio: Float = localTime.minute.toFloat() / 60
        val minutesOffsetY = minuteRatio * (2 * dailyAgendaState.config.slotHeight)

        state.value = CurrentTimeMarkerState(offsetY = (hourOffsetY + minutesOffsetY).dp)
    }

    @OptIn(ExperimentalTime::class)
    fun getCurrentLocalTime(): LocalTime {
        val now = Clock.System.now()
        val timeZone = TimeZone.currentSystemDefault()
        val localDateTime = now.toLocalDateTime(timeZone)
        return localDateTime.time
    }

}
