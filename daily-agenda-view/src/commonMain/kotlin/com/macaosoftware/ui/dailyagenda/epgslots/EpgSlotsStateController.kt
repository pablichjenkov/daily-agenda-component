package com.macaosoftware.ui.dailyagenda.epgslots

import androidx.compose.runtime.mutableStateOf
import com.macaosoftware.ui.dailyagenda.decimalslots.Slot
import com.macaosoftware.ui.dailyagenda.timeslots.fromDecimalValueToTimeText
import com.macaosoftware.ui.dailyagenda.timeslots.toSlotConfig

class EpgSlotsStateController(
    epgChannelSlotsConfig: EpgChannelSlotsConfig
) {

    val timeSlotConfig = epgChannelSlotsConfig.timeSlotConfig
    val slotConfig = timeSlotConfig.toSlotConfig()
    val slotScale = slotConfig.slotScale
    val slotHeight = slotConfig.slotHeight
    val slotUnit = 1.0F / slotScale
    val firstSlotIndex = (slotScale * slotConfig.initialSlotValue.toInt())
    private val amountOfSlotsInOneDay = (slotConfig.lastSlotValue * slotScale).toInt()

    val state = mutableStateOf<EpgChannelSlotsState>(createInitialValue())

    fun createInitialValue(): EpgChannelSlotsState {
        return EpgChannelSlotsState(
            slots = createSlots(firstSlotIndex, amountOfSlotsInOneDay),
            epgChannels = emptyList(),
            epgChannelSlotsConfig = EpgChannelSlotsConfig()
        )
    }

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

}