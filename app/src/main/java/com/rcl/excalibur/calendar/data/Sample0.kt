package com.rcl.excalibur.calendar.data

import com.rcl.excalibur.calendar.Event
import com.rcl.excalibur.calendar.Slot

class Sample0(private val slots: List<Slot>) {

    val slotToEventMap: Map<Slot, List<Event>> = mapOf(
        slots[0] to createEventsFor800AM(startSlot = slots[0]),
        slots[1] to createEventsFor830AM(startSlot = slots[1]),
        slots[2] to createEventsFor900AM(startSlot = slots[2]),
        slots[3] to createEventsFor930AM(startSlot = slots[3]),
        slots[4] to emptyList(),
        slots[5] to emptyList(),
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

    val slotMetadataMap: Map<Slot, Int> = mapOf(
        slots[0] to computeSlotInfo(slot = slots[0]),
        slots[1] to computeSlotInfo(slot = slots[1]),
        slots[2] to computeSlotInfo(slot = slots[2]),
        slots[3] to computeSlotInfo(slot = slots[3]),
        slots[4] to computeSlotInfo(slot = slots[4]),
        slots[5] to computeSlotInfo(slot = slots[5]),
        slots[6] to computeSlotInfo(slot = slots[6]),
        slots[7] to computeSlotInfo(slot = slots[7]),
        slots[8] to computeSlotInfo(slot = slots[8]),
        slots[9] to computeSlotInfo(slot = slots[9]),
        slots[10] to computeSlotInfo(slot = slots[10]),
        slots[11] to computeSlotInfo(slot = slots[11]),
        slots[12] to computeSlotInfo(slot = slots[12]),
        slots[13] to computeSlotInfo(slot = slots[13]),
        slots[14] to computeSlotInfo(slot = slots[14]),
        slots[15] to computeSlotInfo(slot = slots[15]),
        slots[16] to computeSlotInfo(slot = slots[16]),
        slots[17] to computeSlotInfo(slot = slots[17]),
        slots[18] to computeSlotInfo(slot = slots[18]),
        slots[19] to computeSlotInfo(slot = slots[19]),
        slots[20] to computeSlotInfo(slot = slots[20]),
        slots[21] to computeSlotInfo(slot = slots[21]),
        slots[22] to computeSlotInfo(slot = slots[22]),
        slots[23] to computeSlotInfo(slot = slots[23]),
        slots[24] to computeSlotInfo(slot = slots[24]),
    )

    fun computeSlotInfo(slot: Slot): Int {
        var numberOfEvents = slotToEventMap[slot]?.size ?: 0
        for (slotIter in slots) {
            if (slot == slotIter) break

            val earlierEvents = slotToEventMap[slotIter] ?: emptyList()
            numberOfEvents += getOverlappingEvents(slot, earlierEvents)
        }
        return numberOfEvents
    }

    private fun getOverlappingEvents(
        slot: Slot,
        earlierEvents: List<Event>
    ): Int {
        var eventOverlapCount = 0
        earlierEvents.forEach { event ->
            if (event.endTime > slot.time + 0.1F) eventOverlapCount++
        }
        return eventOverlapCount
    }

    fun createEventsFor800AM(startSlot: Slot): List<Event> {
        return listOf(
            Event(
                startSlot = startSlot,
                title = "Ev 1",
                startTime = 8.0F,
                endTime = 11.0F
            ),
            Event(
                startSlot = startSlot,
                title = "Ev 2",
                startTime = 8.0F,
                endTime = 8.50F
            ),
            Event(
                startSlot = startSlot,
                title = "Ev 3",
                startTime = 8.0F,
                endTime = 8.25F
            ),
        )
    }

    fun createEventsFor830AM(startSlot: Slot): List<Event> {
        return listOf(
            Event(
                startSlot = startSlot,
                title = "Evt 4",
                startTime = 8.5F,
                endTime = 11.0F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 5",
                startTime = 8.5F,
                endTime = 9.5F
            ),
        )
    }

    fun createEventsFor900AM(startSlot: Slot): List<Event> {
        return listOf(
            Event(
                startSlot = startSlot,
                title = "Evt 6",
                startTime = 9.0F,
                endTime = 10.0F
            )
        )
    }

    fun createEventsFor930AM(startSlot: Slot): List<Event> {
        return listOf(
            Event(
                startSlot = startSlot,
                title = "Evt 7",
                startTime = 9.5F,
                endTime = 11.0F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 8",
                startTime = 9.5F,
                endTime = 10.5F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 9",
                startTime = 9.5F,
                endTime = 10.0F
            ),
            Event(
                startSlot = startSlot,
                title = "Evt 10",
                startTime = 9.5F,
                endTime = 10.0F
            )
        )
    }

}
