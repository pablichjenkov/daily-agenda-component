package com.macaosoftware.ui.dailyagenda

data class Channel(
    val name: String,
    val events: List<DecimalEvent>
)

data class ChannelSlotsConfig(
    val channelWidth: Int = 64,
    val timeSlotConfig: TimeSlotConfig = TimeSlotConfig()
)

data class ChannelSlotsState(
    val slots: List<Slot>,
    val channels: List<Channel>,
    val channelSlotsConfig: ChannelSlotsConfig
)

internal fun ChannelSlotsConfig.toSlotConfig(): SlotConfig {
    return timeSlotConfig.toSlotConfig()
}
