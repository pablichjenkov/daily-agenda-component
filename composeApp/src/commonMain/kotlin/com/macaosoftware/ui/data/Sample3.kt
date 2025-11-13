package com.macaosoftware.ui.data

import com.macaosoftware.ui.dailyagenda.Event
import com.macaosoftware.ui.dailyagenda.Slot
/*

class Sample3(slotsController: SlotsController) {

    val slots: List<Slot> = slotsController.slots
    val slotToEventMap = mutableMapOf<Slot, List<Event>>()

    init {
        for (i in 0..slots.lastIndex) {
            val slot = slots[i]
            slotToEventMap[slot] = emptyList()
        }

        val slot8_00 = slotsController.getSlotForValue(8.0F)
        slotToEventMap[slot8_00] = createEventsFor800AM(startSlot = slot8_00)

        val slot8_30 = slotsController.getSlotForValue(8.5F)
        slotToEventMap[slot8_30] = createEventsFor830AM(startSlot = slot8_30)

        val slot9_00 = slotsController.getSlotForValue(9.0F)
        slotToEventMap[slot9_00] = createEventsFor900AM(startSlot = slot9_00)

        val slot9_30 = slotsController.getSlotForValue(9.5F)
        slotToEventMap[slot9_30] = createEventsFor930AM(startSlot = slot9_30)

        val slot10_00 = slotsController.getSlotForValue(10.0F)
        slotToEventMap[slot10_00] = createEventsFor10_00AM(startSlot = slot10_00)

        val slot10_30 = slotsController.getSlotForValue(10.5F)
        slotToEventMap[slot10_30] = createEventsFor10_30AM(startSlot = slot10_30)
    }

    private fun createEventsFor800AM(startSlot: Slot): List<Event> {
        return slotToEventMap[startSlot]!! + listOf(
            Event(
                title = "Ev 1",
                startValue = 8.0F,
                endValue = 12.0F
            ),
            Event(
                title = "Ev 2",
                startValue = 8.0F,
                endValue = 10.0F
            ),
            Event(
                title = "Ev 3",
                startValue = 8.0F,
                endValue = 9.0F
            ),
            Event(
                title = "Ev 4",
                startValue = 8.0F,
                endValue = 8.5F
            )
        )
    }

    private fun createEventsFor830AM(startSlot: Slot): List<Event> {
        return slotToEventMap[startSlot]!! + listOf(
            Event(
                title = "Evt 5",
                startValue = 8.5F,
                endValue = 12.5F
            ),
            Event(
                title = "Evt 6",
                startValue = 8.5F,
                endValue = 10.5F
            ),
            Event(
                title = "Evt 7",
                startValue = 8.5F,
                endValue = 9.5F
            ),
            Event(
                title = "Evt 8",
                startValue = 8.5F,
                endValue = 9.0F
            ),
        )
    }

    private fun createEventsFor900AM(startSlot: Slot): List<Event> {
        return slotToEventMap[startSlot]!! + listOf(
            Event(
                title = "Evt 9",
                startValue = 9.0F,
                endValue = 11.0F
            ),
            Event(
                title = "Evt 10",
                startValue = 9.0F,
                endValue = 10.5F
            ),
            Event(
                title = "Evt 11",
                startValue = 9.0F,
                endValue = 10.0F
            ),
            Event(
                title = "Evt 12",
                startValue = 9.0F,
                endValue = 10.0F
            )
        )
    }

    private fun createEventsFor930AM(startSlot: Slot): List<Event> {
        return slotToEventMap[startSlot]!! + listOf(
            Event(
                title = "Evt 13",
                startValue = 9.5F,
                endValue = 11.0F
            ),
            Event(
                title = "Evt 14",
                startValue = 9.5F,
                endValue = 10.5F
            ),
            Event(
                title = "Evt 15",
                startValue = 9.5F,
                endValue = 10.0F
            ),
            Event(
                title = "Evt 16",
                startValue = 9.5F,
                endValue = 10.0F
            )
        )
    }

    private fun createEventsFor10_00AM(startSlot: Slot): List<Event> {
        return slotToEventMap[startSlot]!! + listOf(
            Event(
                title = "Evt 17",
                startValue = 10.0F,
                endValue = 11.5F
            ),
            Event(
                title = "Evt 18",
                startValue = 10.0F,
                endValue = 11.0F
            ),
            Event(
                title = "Evt 19",
                startValue = 10.0F,
                endValue = 10.5F
            ),
            Event(
                title = "Evt 20",
                startValue = 10.0F,
                endValue = 10.5F
            )
        )
    }

    private fun createEventsFor10_30AM(startSlot: Slot): List<Event> {
        return slotToEventMap[startSlot]!! + listOf(
            Event(
                title = "Evt 21",
                startValue = 10.5F,
                endValue = 11.5F
            ),
            Event(
                title = "Evt 22",
                startValue = 10.5F,
                endValue = 11.0F
            ),
            Event(
                title = "Evt 23",
                startValue = 10.5F,
                endValue = 11.0F
            ),
            Event(
                title = "Evt 24",
                startValue = 10.5F,
                endValue = 11.0F
            )
        )
    }

}
*/