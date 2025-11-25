package com.macaosoftware.ui.dailyagenda.decimalslots

open class DecimalSlotsStateController(
    val slotConfig: SlotConfig,
    eventsArrangement: EventsArrangement = EventsArrangement.MixedDirections()
) {

    val slotScale = slotConfig.slotScale
    val slotHeight = slotConfig.slotHeight
    val slotUnit = 1.0F / slotScale
    val firstSlotIndex = (slotScale * slotConfig.initialSlotValue.toInt())

    private val amountOfSlotsInOneDay = slotConfig.lastSlotValue.toInt() * slotScale


    internal val decimalSlotsBaseLayoutStateController = DecimalSlotsBaseLayoutStateController(
        slotConfig = slotConfig,
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
