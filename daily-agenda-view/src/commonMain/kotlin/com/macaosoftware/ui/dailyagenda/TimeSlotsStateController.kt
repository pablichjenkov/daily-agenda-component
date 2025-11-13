package com.macaosoftware.ui.dailyagenda

class TimeSlotsStateController(
    slotConfig: SlotConfig = SlotConfig(),
    eventsArrangement: EventsArrangement = EventsArrangement.MixedDirections()
) : DecimalSlotsStateController(
    slotConfig = slotConfig,
    eventsArrangement = eventsArrangement
) {

    val timeSlotsDataUpdater = TimeSlotsDataUpdater(timeSlotsStateController = this)

    override fun createSlots(
        firstSlotIndex: Int,
        amountOfSlotsInOneDay: Int
    ): List<Slot> {
        val slots = mutableListOf<Slot>()
        for (i in firstSlotIndex until amountOfSlotsInOneDay) {
            val slotStartValue = i * slotUnit
            val title = fromDecimalValueToTimeText(slotStartValue)

            slots.add(
                Slot(
                    title = title,
                    value = slotStartValue
                )
            )
        }
        return slots
    }

}
