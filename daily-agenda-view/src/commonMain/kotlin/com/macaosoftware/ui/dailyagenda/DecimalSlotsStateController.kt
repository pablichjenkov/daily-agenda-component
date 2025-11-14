package com.macaosoftware.ui.dailyagenda

open class DecimalSlotsStateController(
    val slotConfig: SlotConfig,
    eventsArrangement: EventsArrangement = EventsArrangement.MixedDirections()
) {

    val slotScale = slotConfig.slotScale
    val slotHeight = slotConfig.slotHeight
    val slotUnit = 1.0F / slotScale
    val firstSlotIndex = (slotScale * slotConfig.initialSlotValue.toInt())

    private val amountOfSlotsInOneDay = slotConfig.lastSlotValue.toInt() * slotScale


    internal val dailyAgendaStateController = DailyAgendaStateController(
        slotConfig = slotConfig,
        slots = createSlots(firstSlotIndex, amountOfSlotsInOneDay),
        eventsArrangement = eventsArrangement
    )

    val decimalSlotsDataUpdater = DecimalSlotsDataUpdater(dailyAgendaStateController)

    fun createSlots(
        firstSlotIndex: Int,
        amountOfSlotsInOneDay: Int
    ): List<Slot> {
        val slots = mutableListOf<Slot>()
        for (i in firstSlotIndex .. amountOfSlotsInOneDay) {
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
