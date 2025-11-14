package com.macaosoftware.ui.dailyagenda

class TimeSlotsStateController(
    timeSlotConfig: TimeSlotConfig = TimeSlotConfig(),
    eventsArrangement: EventsArrangement = EventsArrangement.MixedDirections()
) {

    val slotConfig = timeSlotConfig.toSlotConfig()
    val slotScale = slotConfig.slotScale
    val slotHeight = slotConfig.slotHeight
    val slotUnit = 1.0F / slotScale
    val firstSlotIndex = (slotScale * slotConfig.initialSlotValue.toInt())

    private val amountOfSlotsInOneDay = (slotConfig.lastSlotValue * slotScale).toInt()

    internal val dailyAgendaStateController = DailyAgendaStateController(
        slotConfig = slotConfig,
        slots = createSlots(firstSlotIndex, amountOfSlotsInOneDay),
        eventsArrangement = eventsArrangement
    )

    val timeSlotsDataUpdater = TimeSlotsDataUpdater(dailyAgendaStateController)

    fun createSlots(
        firstSlotIndex: Int,
        amountOfSlotsInOneDay: Int
    ): List<Slot> {
        val slots = mutableListOf<Slot>()
        for (i in firstSlotIndex .. amountOfSlotsInOneDay) {
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
