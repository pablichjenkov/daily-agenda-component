package com.rcl.excalibur.calendar

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

const val InitialSlotTime = 8.0F // TODO: Use 0.0F as initial slot time
const val SlotUnit = 0.5F // TODO: Use 1 as slot unit
const val SlotHeight = 120
const val TimeTitleLeftPadding = 72

/**
 * Returns the height in Dp for a given Event based on the amount of slots it touches.
 * */
fun getEventHeight(event: Event): Dp {
    val numberOfSlots = (event.endTime - event.startTime) / SlotUnit
    return (numberOfSlots * SlotHeight).dp
}

/**
 * For a given event, it returns the maximum number of sibling events, across all the slots the
 * event touches.
 * */
fun getMaximumNumberOfSiblingsInContainingSlots(event: Event, calendarState: CalendarState): Int {
    val containingSlots = getSlotsRangeForEvent(event, calendarState)
    val maxNumberOfEvents = containingSlots.fold(initial = 1) { maxNumberOfEvents, slot ->
        val numberOfEvents = calendarState.slotMetadataMap[slot] ?: 0
        if (numberOfEvents > maxNumberOfEvents) {
            numberOfEvents
        } else maxNumberOfEvents
    }
    println("LayoutUtil: maxNumberOfEvents: $maxNumberOfEvents")
    return maxNumberOfEvents
}

/**
 * Returns a list of the slots touched by the given event. Including its own start slot.
 * */
fun getSlotsRangeForEvent(
    event: Event,
    calendarState: CalendarState
): List<Slot> {
    val slotIndex = calendarState.slots.indexOf(event.startSlot)
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

/**
 * Returns a list of the slots touched by the given event. Excluding its own start slot.
 * */
fun getSlotsIgnoreStartSlot(
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

internal fun updateEventOffsetX(
    calendarState: CalendarState,
    event: Event,
    slotOffsetInfoMap: Map<Slot, OffsetInfo>,
    eventWidth: Dp,
    isLeft: Boolean
) {

    val eventSlops = getSlotsIgnoreStartSlot(
        calendarState = calendarState,
        event = event
    )
    val currentSlotOffsetInfo = slotOffsetInfoMap[event.startSlot]!!

    if (isLeft) {
        currentSlotOffsetInfo.leftAccumulated += eventWidth
    } else {
        currentSlotOffsetInfo.rightAccumulated += eventWidth
    }

    eventSlops.forEach { slot ->
        val offsetInfo = slotOffsetInfoMap[slot]!!
        if (isLeft) {
            offsetInfo.leftStartOffset = currentSlotOffsetInfo.getTotalLeftOffset()
            println("LayoutUtil: slot: ${slot.title} has a new Left offset of: ${offsetInfo.leftAccumulated}")
        } else {
            offsetInfo.rightStartOffset = currentSlotOffsetInfo.getTotalRightOffset()
            println("LayoutUtil: slot: ${slot.title} has a new Right offset of: ${offsetInfo.rightAccumulated}")
        }
    }
}

fun Event.isSingleSlot(): Boolean {
    return endTime - startTime < 0.6
}
