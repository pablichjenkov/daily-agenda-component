package com.macaosoftware.ui.dailyagenda

open class DecimalSlotsStateController(
    slotConfig: SlotConfig = SlotConfig(),
    eventsArrangement: EventsArrangement = EventsArrangement.MixedDirections()
) : DailyAgendaStateController(
    slotConfig = slotConfig,
    eventsArrangement = eventsArrangement
) {

    val decimalSlotsDataUpdater = DecimalSlotsDataUpdater(decimalSlotsStateController = this)

    override fun createSlots(
        firstSlotIndex: Int,
        amountOfSlotsInOneDay: Int
    ): List<Slot> {
        val slots = mutableListOf<Slot>()
        for (i in firstSlotIndex until amountOfSlotsInOneDay) {
            val slotStartValue = i * slotUnit
            slots.add(
                Slot(
                    title = "$slotStartValue",
                    value = slotStartValue
                )
            )
        }
        return slots
    }

}
