package com.macaosoftware.ui.dailyagenda.epgslots

import com.macaosoftware.ui.dailyagenda.decimalslots.Slot
import com.macaosoftware.ui.dailyagenda.decimalslots.DecimalSlotConfig
import com.macaosoftware.ui.dailyagenda.timeslots.LocalTimeEvent
import com.macaosoftware.ui.dailyagenda.timeslots.TimeSlotConfig
import com.macaosoftware.ui.dailyagenda.timeslots.toSlotConfig
import kotlinx.datetime.LocalTime

data class EpgSlotsState(
    val slots: List<Slot>,
    val epgChannels: List<EpgChannel>,
    val epgChannelSlotConfig: EpgChannelSlotConfig
)

data class EpgChannel(
    val name: String,
    val events: List<LocalTimeEvent>
)

data class EpgChannelSlotConfig(
    val channelWidth: Int = 88,
    val topHeaderHeight: Int = 56,
    val timeSlotConfig: TimeSlotConfig = TimeSlotConfig(
        startSlotTime = LocalTime(0, 0),
        endSlotTime = LocalTime(23, 59),
        useAmPm = true,
        slotScale = 2,
        slotHeight = 48,
        timelineLeftPadding = 72
    )
)

internal fun EpgChannelSlotConfig.toSlotConfig(): DecimalSlotConfig {
    return timeSlotConfig.toSlotConfig()
}
