package com.macaosoftware.ui.dailyagenda.epgslots

import com.macaosoftware.ui.dailyagenda.decimalslots.DecimalEvent
import com.macaosoftware.ui.dailyagenda.decimalslots.Slot
import com.macaosoftware.ui.dailyagenda.decimalslots.SlotConfig
import com.macaosoftware.ui.dailyagenda.timeslots.LocalTimeEvent
import com.macaosoftware.ui.dailyagenda.timeslots.TimeSlotConfig
import com.macaosoftware.ui.dailyagenda.timeslots.toSlotConfig

data class EpgSlotsState(
    val slots: List<Slot>,
    val epgChannels: List<EpgChannel>,
    val epgChannelSlotsConfig: EpgChannelSlotsConfig
)

data class EpgChannel(
    val name: String,
    val events: List<LocalTimeEvent>
)

data class EpgChannelSlotsConfig(
    val channelWidth: Int = 64,
    val timeSlotConfig: TimeSlotConfig = TimeSlotConfig()
)

internal fun EpgChannelSlotsConfig.toSlotConfig(): SlotConfig {
    return timeSlotConfig.toSlotConfig()
}
