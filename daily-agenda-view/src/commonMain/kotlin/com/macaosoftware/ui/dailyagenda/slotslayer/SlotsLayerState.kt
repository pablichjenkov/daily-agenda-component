package com.macaosoftware.ui.dailyagenda.slotslayer

import com.macaosoftware.ui.dailyagenda.decimalslots.DecimalSlotsBaseLayoutState
import com.macaosoftware.ui.dailyagenda.decimalslots.Slot
import com.macaosoftware.ui.dailyagenda.epgslots.EpgSlotsState

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

internal fun EpgSlotsState.getSlotsLayerState(): SlotsLayerState {
    return SlotsLayerState(
        slots = slots,
        slotHeight = epgChannelSlotConfig.timeSlotConfig.slotHeight
    )
}