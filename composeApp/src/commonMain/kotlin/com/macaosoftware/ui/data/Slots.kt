package com.macaosoftware.ui.data

import com.macaosoftware.ui.dailyagenda.Config
import com.macaosoftware.ui.dailyagenda.EventWidthType
import com.macaosoftware.ui.dailyagenda.Slot

object Slots {
    val slotList = mutableListOf<Slot>()
    val amountOfhours = 24
    val slotScale = 2
    val amountOfSlots = amountOfhours * slotScale
    val slotUnit = 1.0F / slotScale

    init {
        for (i in 0 until amountOfSlots) {

            val slotStartValue = i * slotUnit

            val titleSuffix = if (slotStartValue < 12F) "AM" else "PM"

            slotList.add(
                Slot(
                    title = "$slotStartValue $titleSuffix",
                    time = slotStartValue,
                    index = i
                )
            )
        }
    }

    val slots = slotList

    val demoConfigLTR = Config.LeftToRight(
        lastEventFillRow = true,
        initialSlotValue = 0.0F,
        slotScale = 2F,
        slotHeight = 96,
        timelineLeftPadding = 72
    )

    val demoConfigRTL = Config.RightToLeft(
        lastEventFillRow = true,
        initialSlotValue = 0.0F,
        slotScale = 2F,
        slotHeight = 96,
        timelineLeftPadding = 72
    )

    val demoConfigMixedDirections = Config.MixedDirections(
        eventWidthType = EventWidthType.FixedSizeFillLastEvent,
        initialSlotValue = 0.0F,
        slotScale = 2F,
        slotHeight = 96,
        timelineLeftPadding = 72
    )
}
