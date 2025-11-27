package com.macaosoftware.ui.dailyagenda.decimalslots

open class DecimalSlotsStateController(
    val decimalSlotConfig: DecimalSlotConfig,
    eventsArrangement: EventsArrangement = EventsArrangement.MixedDirections()
) {

    val slotScale = decimalSlotConfig.slotScale
    val slotHeight = decimalSlotConfig.slotHeight
    val slotUnit = 1.0F / slotScale
    val firstSlotIndex = (slotScale * decimalSlotConfig.initialSlotValue.toInt())

    private val amountOfSlotsInOneDay = decimalSlotConfig.lastSlotValue.toInt() * slotScale


    internal val decimalSlotsBaseLayoutStateController = DecimalSlotsBaseLayoutStateController(
        decimalSlotConfig = decimalSlotConfig,
        slots = createSlots(firstSlotIndex, amountOfSlotsInOneDay),
        eventsArrangement = eventsArrangement
    )

    val decimalSlotsDataUpdater = DecimalSlotsDataUpdater(decimalSlotsBaseLayoutStateController)

    fun createSlots(
        firstSlotIndex: Int,
        amountOfSlotsInOneDay: Int
    ): List<Slot> {
        val slots = mutableListOf<Slot>()
        for (i in firstSlotIndex..amountOfSlotsInOneDay) {
            val slotStartValue = i * slotUnit
            slots.add(
                Slot(
                    title = "$slotStartValue",
                    startValue = slotStartValue,
                    endValue = slotStartValue + slotUnit
                )
            )
        }
        return slots
    }

    fun getTimeSlotsData(): Map<Slot, List<DecimalEvent>> {
        return decimalSlotsBaseLayoutStateController.slotToDecimalEventMapSorted
    }

}
