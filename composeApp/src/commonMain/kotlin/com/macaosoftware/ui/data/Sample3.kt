package com.macaosoftware.ui.data

import com.macaosoftware.ui.dailyagenda.Event
import com.macaosoftware.ui.dailyagenda.Slot

class Sample3(private val slots: List<Slot>) {

    val slotToEventMap: Map<Slot, List<Event>> = mapOf(
        slots[0] to createEventsFor800AM(startSlot = slots[0]),
        slots[1] to createEventsFor830AM(startSlot = slots[1]),
        slots[2] to createEventsFor900AM(startSlot = slots[2]),
        slots[3] to createEventsFor930AM(startSlot = slots[3]),
        slots[4] to createEventsFor10_00AM(startSlot = slots[4]),
        slots[5] to createEventsFor10_30AM(startSlot = slots[5]),
        slots[6] to emptyList(),
        slots[7] to emptyList(),
        slots[8] to emptyList(),
        slots[9] to emptyList(),
        slots[10] to emptyList(),
        slots[11] to emptyList(),
        slots[12] to emptyList(),
        slots[13] to emptyList(),
        slots[14] to emptyList(),
        slots[15] to emptyList(),
        slots[16] to emptyList(),
        slots[17] to emptyList(),
        slots[18] to emptyList(),
        slots[19] to emptyList(),
        slots[20] to emptyList(),
        slots[21] to emptyList(),
        slots[22] to emptyList(),
        slots[23] to emptyList(),
        slots[24] to emptyList()
    )

    private fun createEventsFor800AM(startSlot: Slot): List<Event> {
        return listOf(
            Event(
                startSlot = startSlot,
                title = "Ev 1",
                startTime = 8.0F,
                endTime = 12.0F
            ),
            Event(
                startSlot = startSlot,
                title = "Ev 2",
                startTime = 8.0F,
                endTime = 10.0F
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
                endTime = 8.5F
            )
        )
    }

    private fun createEventsFor830AM(startSlot: Slot): List<Event> {
        return listOf(
            Event(
                startSlot = startSlot,
                title = "Evt 5",
                startTime = 8.5F,
                endTime = 12.5F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 6",
                startTime = 8.5F,
                endTime = 10.5F
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

    private fun createEventsFor900AM(startSlot: Slot): List<Event> {
        return listOf(
            Event(
                startSlot = startSlot,
                title = "Evt 9",
                startTime = 9.0F,
                endTime = 11.0F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 10",
                startTime = 9.0F,
                endTime = 10.5F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 11",
                startTime = 9.0F,
                endTime = 10.0F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 12",
                startTime = 9.0F,
                endTime = 10.0F
            )
        )
    }

    private fun createEventsFor930AM(startSlot: Slot): List<Event> {
        return listOf(
            Event(
                startSlot = startSlot,
                title = "Evt 13",
                startTime = 9.5F,
                endTime = 11.0F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 14",
                startTime = 9.5F,
                endTime = 10.5F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 15",
                startTime = 9.5F,
                endTime = 10.0F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 16",
                startTime = 9.5F,
                endTime = 10.0F
            )
        )
    }

    private fun createEventsFor10_00AM(startSlot: Slot): List<Event> {
        return listOf(
            Event(
                startSlot = startSlot,
                title = "Evt 17",
                startTime = 10.0F,
                endTime = 11.5F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 18",
                startTime = 10.0F,
                endTime = 11.0F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 19",
                startTime = 10.0F,
                endTime = 10.5F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 20",
                startTime = 10.0F,
                endTime = 10.5F
            )
        )
    }

    private fun createEventsFor10_30AM(startSlot: Slot): List<Event> {
        return listOf(
            Event(
                startSlot = startSlot,
                title = "Evt 21",
                startTime = 10.5F,
                endTime = 11.5F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 22",
                startTime = 10.5F,
                endTime = 11.0F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 23",
                startTime = 10.5F,
                endTime = 11.0F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 24",
                startTime = 10.5F,
                endTime = 11.0F
            )
        )
    }

}
