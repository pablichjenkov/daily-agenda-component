package com.macaosoftware.ui.dailyagenda

import kotlin.math.abs

abstract class SlotsGenerator {
    val amountOfHours = 24
    val slotScale = 2
    val amountOfSlots = amountOfHours * slotScale
    val slotUnit = 1.0F / slotScale
    val slots = createSlots()

    abstract fun createSlots(): List<Slot>

    fun getSlotForTime(startTime: Float): Slot {
        return slots.find { abs(x = startTime - it.time) < slotUnit }
            ?: error("startTime must be between 0.0 and 24.0")
    }

}

class DecimalSlotsGenerator() : SlotsGenerator() {

    override fun createSlots(): List<Slot> {
        val slots = mutableListOf<Slot>()
        for (i in 0 until amountOfSlots) {
            val slotStartValue = i * slotUnit
            slots.add(
                Slot(
                    title = "$slotStartValue",
                    time = slotStartValue
                )
            )
        }
        return slots
    }
}

class TimeLineSlotsGenerator() : SlotsGenerator() {

    override fun createSlots(): List<Slot> {
        val slots = mutableListOf<Slot>()
        for (i in 0 until amountOfSlots) {
            val slotStartValue = i * slotUnit
            val title = fromDecimalValueToTimeText(slotStartValue)

            slots.add(
                Slot(
                    title = title,
                    time = slotStartValue
                )
            )
        }
        return slots
    }
}
