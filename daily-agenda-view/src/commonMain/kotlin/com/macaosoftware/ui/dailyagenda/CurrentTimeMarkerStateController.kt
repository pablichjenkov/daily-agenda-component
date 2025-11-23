package com.macaosoftware.ui.dailyagenda

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class CurrentTimeMarkerState(val offsetY: Dp)

internal class CurrentTimeMarkerStateController(
    slotConfig: SlotConfig
) {

    val state = mutableStateOf(value = CurrentTimeMarkerState(offsetY = 0.dp))

    init {
        val localTime = getCurrentLocalTime()
        val currentTimeAsDecimal = fromLocalTimeToValue(localTime)
        val offsetY =
            (currentTimeAsDecimal - slotConfig.initialSlotValue) * (slotConfig.slotScale * slotConfig.slotHeight)


        state.value = CurrentTimeMarkerState(offsetY = offsetY.dp)
    }

    @OptIn(ExperimentalTime::class)
    fun getCurrentLocalTime(): LocalTime {
        val now = Clock.System.now()
        val timeZone = TimeZone.currentSystemDefault()
        val localDateTime = now.toLocalDateTime(timeZone)
        return localDateTime.time
    }

}
