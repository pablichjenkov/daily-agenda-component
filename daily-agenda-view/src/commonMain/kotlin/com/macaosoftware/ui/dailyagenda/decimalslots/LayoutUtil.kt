package com.macaosoftware.ui.dailyagenda.decimalslots

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max

/**
 * Returns the Event Y axis offset in Dp from the slot start time.
 * */
internal fun getEventTranslationInSlot(decimalEvent: DecimalEvent, eventSlot: Slot, config: Config): Dp {
    val fractionOfSlots = (decimalEvent.startValue - eventSlot.startValue) * config.slotScale
    return (fractionOfSlots * config.slotHeight).dp
}

/**
 * Returns the height in Dp for a given Event based on the amount of slots it touches.
 * */
internal fun getEventHeight(decimalEvent: DecimalEvent, config: Config): Dp {
    val numberOfSlots = (decimalEvent.endValue - decimalEvent.startValue) * config.slotScale
    return (numberOfSlots * config.slotHeight).dp
}

/**
 * For a given event, it returns the maximum number of sibling events, across all the slots the
 * event touches.
 * */
// TODO: This information can be computed in the data setup and saved in a
// Map<Event, MaximumNumberOfSiblingsInContainingSlots>. Avoiding the iterations
// during composition.
private fun getMaximumNumberOfSiblingsInContainingSlots(
    decimalEvent: DecimalEvent,
    eventSlot: Slot,
    decimalSlotsBaseLayoutState: DecimalSlotsBaseLayoutState
): Int {
    val containingSlots = getSlotsIncludeStartSlot(decimalEvent, eventSlot, decimalSlotsBaseLayoutState.slots)
    val maxNumberOfEvents = containingSlots.fold(initial = 0) { maxNumberOfEvents, slot ->
        val numberOfEvents = decimalSlotsBaseLayoutState.slotInfoMap[slot]?.getTotalColumnSpans() ?: 0
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
internal fun getSlotsIncludeStartSlot(
    decimalEvent: DecimalEvent,
    eventSlot: Slot,
    slots: List<Slot>
): List<Slot> {
    val slotIndex = slots.indexOf(eventSlot)
    val eventSlots = slots.subList(slotIndex, slots.size)
    val containingSlots = mutableListOf<Slot>()
    eventSlots.forEach { slot ->
        if (decimalEvent.endValue > slot.startValue + 0.0001) {
            containingSlots.add(slot)
        }
    }
    return containingSlots
}

/**
 * Returns a list of the slots touched by the given event. Excluding its own start slot.
 * */
internal fun getSlotsIgnoreStartSlot(
    decimalSlotsBaseLayoutState: DecimalSlotsBaseLayoutState,
    decimalEvent: DecimalEvent,
    eventSlot: Slot
): List<Slot> {
    val slotIndex = decimalSlotsBaseLayoutState.slots.indexOf(eventSlot)
    val laterSlots = decimalSlotsBaseLayoutState.slots.subList(slotIndex + 1, decimalSlotsBaseLayoutState.slots.size)
    val containingSlots = mutableListOf<Slot>()
    laterSlots.forEach { slot ->
        if (decimalEvent.endValue > slot.startValue + 0.0001) {
            containingSlots.add(slot)
        }
    }
    return containingSlots
}

internal fun updateEventOffsetX(
    decimalSlotsBaseLayoutState: DecimalSlotsBaseLayoutState,
    decimalEvent: DecimalEvent,
    eventSlot: Slot,
    slotOffsetInfoMap: Map<Slot, OffsetInfo>,
    eventWidth: Dp,
    isLeft: Boolean
) {

    val eventSlops = getSlotsIgnoreStartSlot(
        decimalSlotsBaseLayoutState = decimalSlotsBaseLayoutState,
        decimalEvent = decimalEvent,
        eventSlot = eventSlot
    )
    val currentSlotOffsetInfo = slotOffsetInfoMap[eventSlot]!!

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

internal fun DecimalEvent.isSingleSlot(eventSlot: Slot): Boolean {
    return eventSlot.endValue >= endValue
}

internal fun getEventWidthFromLeft(
    decimalSlotsBaseLayoutState: DecimalSlotsBaseLayoutState,
    decimalEvent: DecimalEvent,
    eventSlot: Slot,
    amountOfEventsInSameSlot: Int,
    currentEventIndex: Int,
    eventContainerWidth: Dp,
    offsetInfo: OffsetInfo,
    slotRemainingWidth: Dp,
    minimumWidth: Dp
): Dp {
    if (shouldReturnMinimumAllowedWidth(decimalSlotsBaseLayoutState.config, decimalEvent, eventSlot)) {
        return minimumWidth
    }

    var singleSlotWidth: Dp? = null

    val eventWidth = if (decimalEvent.isSingleSlot(eventSlot)) {
        singleSlotWidth ?: run {
            val amountOfSingleSlotEvents = (amountOfEventsInSameSlot - currentEventIndex)
            ((eventContainerWidth - offsetInfo.getTotalLeftOffset() - offsetInfo.rightStartOffset) / amountOfSingleSlotEvents).also {
                singleSlotWidth = it
            }
        }
    } else {
        val widthNumber = getMaximumNumberOfSiblingsInContainingSlots(
            decimalEvent,
            eventSlot,
            decimalSlotsBaseLayoutState
        )
        (slotRemainingWidth / widthNumber)
    }

    return max(minimumWidth, eventWidth)
}

internal fun getEventWidthFromRight(
    decimalSlotsBaseLayoutState: DecimalSlotsBaseLayoutState,
    decimalEvent: DecimalEvent,
    eventSlot: Slot,
    amountOfEventsInSameSlot: Int,
    currentEventIndex: Int,
    eventContainerWidth: Dp,
    offsetInfo: OffsetInfo,
    slotRemainingWidth: Dp,
    minimumWidth: Dp
): Dp {
    if (shouldReturnMinimumAllowedWidth(decimalSlotsBaseLayoutState.config, decimalEvent, eventSlot)) {
        return minimumWidth
    }

    var singleSlotWidth: Dp? = null

    val eventWidth: Dp = if (decimalEvent.isSingleSlot(eventSlot)) {
        singleSlotWidth ?: run {
            val amountOfSingleSlotEvents = (amountOfEventsInSameSlot - currentEventIndex)
            ((eventContainerWidth - offsetInfo.leftStartOffset - offsetInfo.getTotalRightOffset()) / amountOfSingleSlotEvents).also {
                singleSlotWidth = it
            }
        }
    } else {
        val widthNumber = getMaximumNumberOfSiblingsInContainingSlots(
            decimalEvent,
            eventSlot,
            decimalSlotsBaseLayoutState
        )
        (slotRemainingWidth / widthNumber)
    }

    return max(minimumWidth, eventWidth)
}

private fun shouldReturnMinimumAllowedWidth(
    config: Config,
    decimalEvent: DecimalEvent,
    eventSlot: Slot
): Boolean {
    when (val eventsArrangement = config.eventsArrangement) {
        is EventsArrangement.LeftToRight -> {
            if (!eventsArrangement.lastEventFillRow) {
                return true
            }
            if (!decimalEvent.isSingleSlot(eventSlot)) {
                return true
            }
            return false
        }

        is EventsArrangement.MixedDirections -> {
            return when (eventsArrangement.eventWidthType) {
                EventWidthType.MaxVariableSize -> false
                EventWidthType.FixedSize -> true
                EventWidthType.FixedSizeFillLastEvent -> !decimalEvent.isSingleSlot(eventSlot)
            }

        }

        is EventsArrangement.RightToLeft -> {
            if (!eventsArrangement.lastEventFillRow) {
                return true
            }
            if (!decimalEvent.isSingleSlot(eventSlot)) {
                return true
            }
            return false
        }
    }
}
