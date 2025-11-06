package com.macaosoftware.ui.data

import com.macaosoftware.ui.dailyagenda.Config
import com.macaosoftware.ui.dailyagenda.EventWidthType
import com.macaosoftware.ui.dailyagenda.Slot

object Slots {

    val slots = listOf(
        Slot(title = "8.00 AM", time = 8.0F),
        Slot(title = "8.30 AM", time = 8.5F),
        Slot(title = "9.00 AM", time = 9.0F),
        Slot(title = "9.30 AM", time = 9.5F),
        Slot(title = "10.00 AM", time = 10.0F),
        Slot(title = "10.30 AM", time = 10.5F),
        Slot(title = "11.00 AM", time = 11.0F),
        Slot(title = "11.30 AM", time = 11.5F),

        Slot(title = "12.00 PM", time = 12.0F),
        Slot(title = "12.30 PM", time = 12.5F),
        Slot(title = "1.00 PM", time = 13.0F),
        Slot(title = "1.30 PM", time = 13.5F),
        Slot(title = "2.00 PM", time = 14.0F),
        Slot(title = "2.30 PM", time = 14.5F),
        Slot(title = "3.00 PM", time = 15.0F),
        Slot(title = "3.50 PM", time = 15.5F),
        Slot(title = "4.00 PM", time = 16.0F),
        Slot(title = "4.30 PM", time = 16.5F),
        Slot(title = "5.00 PM", time = 17.0F),
        Slot(title = "5.30 PM", time = 17.5F),
        Slot(title = "6.00 PM", time = 18.0F),
        Slot(title = "6.30 PM", time = 18.5F),
        Slot(title = "7.00 PM", time = 19.0F),
        Slot(title = "7.30 PM", time = 19.5F),
        Slot(title = "8.00 PM", time = 20.0F),
        Slot(title = "8.30 PM", time = 20.5F),
        Slot(title = "9.00 PM", time = 21.0F),
        Slot(title = "9.30 PM", time = 21.5F),
        Slot(title = "10.00 PM", time = 22.0F),
        Slot(title = "10.30 PM", time = 22.5F),
        Slot(title = "11.00 PM", time = 23.0F),
        Slot(title = "11.30 PM", time = 23.5F),

        Slot(title = "12.00 AM", time = 24.0F),
        Slot(title = "12.30 AM", time = 24.5F),
        Slot(title = "1.00 AM", time = 25.0F),
        Slot(title = "1.30 AM", time = 25.5F),
        Slot(title = "2.00 AM", time = 26.0F),
        Slot(title = "2.30 AM", time = 26.5F),
        Slot(title = "3.00 AM", time = 27.0F),
        Slot(title = "3.30 AM", time = 27.5F),
        Slot(title = "4.00 AM", time = 28.0F),
        Slot(title = "4.30 AM", time = 28.5F),
        Slot(title = "5.00 AM", time = 29.0F)
    )

    val demoConfigLTR = Config.LeftToRight(
        lastEventFillRow = true,
        initialSlotValue = 8.0F,
        slotUnit = 0.5F,
        slotHeight = 96,
        timelineLeftPadding = 72
    )

    val demoConfigRTL = Config.RightToLeft(
        lastEventFillRow = true,
        initialSlotValue = 8.0F,
        slotUnit = 0.5F,
        slotHeight = 96,
        timelineLeftPadding = 72
    )

    val demoConfigMixedDirections = Config.MixedDirections(
        eventWidthType = EventWidthType.FixedSizeFillLastEvent,
        initialSlotValue = 8.0F,
        slotUnit = 0.5F,
        slotHeight = 96,
        timelineLeftPadding = 72
    )
}
