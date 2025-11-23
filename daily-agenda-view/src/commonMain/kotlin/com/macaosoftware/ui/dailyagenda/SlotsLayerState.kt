package com.macaosoftware.ui.dailyagenda

data class SlotsLayerState(
    val slots: List<Slot>,
    val slotHeight: Int
)

internal fun DailyAgendaState.getSlotsLayerState(): SlotsLayerState {
    return SlotsLayerState(
        slots = slots,
        slotHeight = config.slotHeight
    )
}

internal fun ChannelSlotsState.getSlotsLayerState(): SlotsLayerState {
    return SlotsLayerState(
        slots = slots,
        slotHeight = channelSlotsConfig.timeSlotConfig.slotHeight
    )
}