package com.macaosoftware.ui.dailyagenda.slotslayer

import com.macaosoftware.ui.dailyagenda.decimalslots.DecimalSlotsBaseLayoutState
import com.macaosoftware.ui.dailyagenda.decimalslots.Slot
import com.macaosoftware.ui.dailyagenda.epgslots.EpgChannelSlotsState

data class SlotsLayerState(
    val slots: List<Slot>,
    val slotHeight: Int
)

internal fun DecimalSlotsBaseLayoutState.getSlotsLayerState(): SlotsLayerState {
    return SlotsLayerState(
        slots = slots,
        slotHeight = config.slotHeight
    )
}

internal fun EpgChannelSlotsState.getSlotsLayerState(): SlotsLayerState {
    return SlotsLayerState(
        slots = slots,
        slotHeight = epgChannelSlotsConfig.timeSlotConfig.slotHeight
    )
}