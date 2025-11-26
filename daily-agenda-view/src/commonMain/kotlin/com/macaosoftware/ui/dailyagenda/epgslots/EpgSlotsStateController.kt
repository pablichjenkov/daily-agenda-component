package com.macaosoftware.ui.dailyagenda.epgslots

import androidx.compose.runtime.mutableStateOf
import com.macaosoftware.ui.dailyagenda.decimalslots.DecimalSlotsBaseLayoutState
import com.macaosoftware.ui.dailyagenda.decimalslots.Slot
import com.macaosoftware.ui.dailyagenda.timeslots.fromDecimalValueToTimeText
import com.macaosoftware.ui.dailyagenda.timeslots.toSlotConfig

class EpgSlotsStateController(
    val epgChannelSlotsConfig: EpgChannelSlotsConfig
) {

    val timeSlotConfig = epgChannelSlotsConfig.timeSlotConfig
    val slotConfig = timeSlotConfig.toSlotConfig()
    val slotScale = slotConfig.slotScale
    val slotHeight = slotConfig.slotHeight
    val slotUnit = 1.0F / slotScale
    val firstSlotIndex = (slotScale * slotConfig.initialSlotValue.toInt())
    private val amountOfSlotsInOneDay = (slotConfig.lastSlotValue * slotScale).toInt()

    private val slots = createSlots(firstSlotIndex, amountOfSlotsInOneDay)

    internal val epgChannels: MutableList<EpgChannel> = mutableListOf()

    val state = mutableStateOf<EpgSlotsState?>(value = null)

    val epgSlotsDataUpdater = EpgSlotsDataUpdater(
        epgSlotsStateController = this
    )

    fun createSlots(
        firstSlotIndex: Int,
        amountOfSlotsInOneDay: Int
    ): List<Slot> {
        val slots = mutableListOf<Slot>()
        for (i in firstSlotIndex..amountOfSlotsInOneDay) {
            val slotStartValue = i * slotUnit
            val title = fromDecimalValueToTimeText(slotStartValue, timeSlotConfig.useAmPm)

            slots.add(
                Slot(
                    title = title,
                    startValue = slotStartValue,
                    endValue = slotStartValue + slotUnit
                )
            )
        }
        return slots
    }

    private fun computeNextState(): EpgSlotsState {
        return EpgSlotsState(
            slots = slots,
            epgChannels = epgChannels,
            epgChannelSlotsConfig = epgChannelSlotsConfig
        )
    }

    internal fun updateState() {
        state.value = computeNextState()
    }

}