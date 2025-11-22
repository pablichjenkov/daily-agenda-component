package com.macaosoftware.ui.dailyagenda

class TimeSlotsStateController(
    val timeSlotConfig: TimeSlotConfig = TimeSlotConfig(),
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

    val timeSlotsDataUpdater = TimeSlotsDataUpdater(
        dailyAgendaStateController = dailyAgendaStateController
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

    fun getTimeSlotsData(): Map<LocalTimeSlot, List<LocalTimeEvent>> {
        val result = mutableMapOf<LocalTimeSlot, List<LocalTimeEvent>>()
        dailyAgendaStateController.slotToDecimalEventMapSorted.forEach { entry ->
            result.put(
                key = entry.key.toLocalTimeSlot(),
                value = entry.value.map { it.toLocalTimeEvent() }
            )
        }
        return result
    }

}
