package com.macaosoftware.ui.data

import com.macaosoftware.ui.dailyagenda.Event
import com.macaosoftware.ui.dailyagenda.Slot

class Sample1(private val slots: List<Slot>) {

    val slotToEventMap = mutableMapOf<Slot, List<Event>>()

    init {
        for (i in 0 .. slots.lastIndex) {
            val slot = slots[i]
            slotToEventMap[slot] = emptyList()
        }

        val slot8_00 = slots[8* Slots.slotScale]
        slotToEventMap[slot8_00] = createEventsFor800AM(startSlot = slot8_00)

        val slot8_30 = slots[8* Slots.slotScale + 1]
        slotToEventMap[slot8_30] = createEventsFor830AM(startSlot = slot8_30)

        val slot9_00 = slots[9* Slots.slotScale]
        slotToEventMap[slot9_00] = createEventsFor900AM(startSlot = slot9_00)

        val slot9_30 = slots[9* Slots.slotScale + 1]
        slotToEventMap[slot9_30] = createEventsFor930AM(startSlot = slot9_30)

        val slot10_00 = slots[10* Slots.slotScale]
        slotToEventMap[slot10_00] = createEventsFor10_00AM(startSlot = slot10_00)

        val slot10_30 = slots[10* Slots.slotScale + 1]
        slotToEventMap[slot10_30] = createEventsFor10_30AM(startSlot = slot10_30)
    }

    fun createEventsFor800AM(startSlot: Slot): List<Event> {
        return listOf(
            Event(
                startSlot = startSlot,
                title = "Ev 1",
                startTime = 8.0F,
                endTime = 10.0F
            ),
            Event(
                startSlot = startSlot,
                title = "Ev 2",
                startTime = 8.0F,
                endTime = 9.5F
            ),
            Event(
                startSlot = startSlot,
                title = "Ev 3",
                startTime = 8.0F,
                endTime = 9.0F
            ),
            Event(
                startSlot = startSlot,
                title = "Ev 4",
                startTime = 8.0F,
                endTime = 8.75F
            ),
            Event(
                startSlot = startSlot,
                title = "Ev 5",
                startTime = 8.0F,
                endTime = 8.25F
            ),
        )
    }

    fun createEventsFor830AM(startSlot: Slot): List<Event> {
        return listOf(
            Event(
                startSlot = startSlot,
                title = "Evt 6",
                startTime = 8.5F,
                endTime = 11.0F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 7",
                startTime = 8.5F,
                endTime = 9.5F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 8",
                startTime = 8.5F,
                endTime = 9.0F
            ),
        )
    }

    fun createEventsFor900AM(startSlot: Slot): List<Event> {
        return listOf(
            Event(
                startSlot = startSlot,
                title = "Evt 9",
                startTime = 9.0F,
                endTime = 10.0F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 10",
                startTime = 9.0F,
                endTime = 10.0F
            )
        )
    }

    fun createEventsFor930AM(startSlot: Slot): List<Event> {
        return listOf(
            Event(
                startSlot = startSlot,
                title = "Evt 11",
                startTime = 9.5F,
                endTime = 11.0F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 12",
                startTime = 9.5F,
                endTime = 10.5F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 13",
                startTime = 9.5F,
                endTime = 10.0F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 14",
                startTime = 9.5F,
                endTime = 10.0F
            )
        )
    }

    fun createEventsFor10_00AM(startSlot: Slot): List<Event> {
        return listOf(
            Event(
                startSlot = startSlot,
                title = "Evt 15",
                startTime = 10.0F,
                endTime = 11.5F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 16",
                startTime = 10.0F,
                endTime = 11.0F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 17",
                startTime = 10.0F,
                endTime = 10.5F
            )
        )
    }

    fun createEventsFor10_30AM(startSlot: Slot): List<Event> {
        return listOf(
            Event(
                startSlot = startSlot,
                title = "Evt 18",
                startTime = 10.5F,
                endTime = 11.5F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 19",
                startTime = 10.5F,
                endTime = 11.0F
            )
        )
    }

}
