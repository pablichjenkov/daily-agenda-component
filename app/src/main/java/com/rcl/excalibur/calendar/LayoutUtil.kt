package com.rcl.excalibur.calendar

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

const val SlotHeight = 120
const val TimeTitleLeftPadding = 72

fun getEventHeight(event: Event): Dp {
    val numberOfSlots = (event.endTime - event.startTime) / 0.5
    return (numberOfSlots * SlotHeight).dp
}

fun getEventWidth(event: Event, slot: Slot, calendarState: CalendarState): Int {
    val containingSlots = getSlotsRangeForEvent(event, slot, calendarState)
    val maxNumberOfEvents = containingSlots.fold(initial = 1) { maxNumberOfEvents, slot ->
        val numberOfEvents = calendarState.slotMetadataMap[slot] ?: 0
        if (numberOfEvents > maxNumberOfEvents) {
            numberOfEvents
        } else maxNumberOfEvents
    }
    println("LayoutUtil: maxNumberOfEvents: $maxNumberOfEvents")
    return maxNumberOfEvents
}

fun getSlotsRangeForEvent(
    event: Event,
    slot: Slot,
    calendarState: CalendarState
): List<Slot> {
    val slotIndex = calendarState.slots.indexOf(slot)
    val eventSlots = calendarState.slots.subList(slotIndex, calendarState.slots.size)
    val containingSlots = mutableListOf<Slot>()
    eventSlots.forEach { slot ->
        println("LayoutUtil: Checking slot: ${slot.title}")
        if (event.endTime > slot.time + 0.1) {
            println("LayoutUtil: slot: ${slot.title} contains event: ${event.title}")
            containingSlots.add(slot)
        }
    }
    return containingSlots
}

internal fun updateEventOffsetX(
    calendarState: CalendarState,
    event: Event,
    slotOffsetInfoMap: Map<Slot, OffsetInfo>,
    eventWidth: Dp
) {

    val eventSlops = getSlopsIgnoreStartSlop(
        calendarState = calendarState,
        event = event
    )
    val currentSlotOffsetInfo = slotOffsetInfoMap[event.startSlot]!!
    currentSlotOffsetInfo.accumulatedOffset += eventWidth

    eventSlops.forEach { slot ->
        if (slotOffsetInfoMap.contains(slot)) {
            val offsetInfo = slotOffsetInfoMap[slot]!!
            offsetInfo.startOffset = currentSlotOffsetInfo.getTotalOffset()
            println("LayoutUtil: slot: ${slot.title} has a new offset of: ${offsetInfo.startOffset}")
        }
    }
}

fun getSlopsIgnoreStartSlop(
    calendarState: CalendarState,
    event: Event
): List<Slot> {
    val slotIndex = calendarState.slots.indexOf(event.startSlot)
    val laterSlots = calendarState.slots.subList(slotIndex + 1, calendarState.slots.size)
    val containingSlots = mutableListOf<Slot>()
    laterSlots.forEach { slot ->
        if (event.endTime > slot.time + 0.1) {
            println("LayoutUtil: slot: ${slot.title} contains event: ${event.title}")
            containingSlots.add(slot)
        }
    }
    return containingSlots
}

fun isSingleSlot(event: Event): Boolean {
    return event.endTime - event.startTime < 0.6
}
