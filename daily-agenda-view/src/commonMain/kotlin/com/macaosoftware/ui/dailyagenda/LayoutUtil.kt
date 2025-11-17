package com.macaosoftware.ui.dailyagenda

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max

/**
 * Returns the Event Y axis offset in Dp from the slot start time.
 * */
internal fun getEventTranslationInSlot(event: Event, eventSlot: Slot, config: Config): Dp {
    val fractionOfSlots = (event.startValue - eventSlot.startValue) * config.slotScale
    return (fractionOfSlots * config.slotHeight).dp
}

/**
 * Returns the height in Dp for a given Event based on the amount of slots it touches.
 * */
internal fun getEventHeight(event: Event, config: Config): Dp {
    val numberOfSlots = (event.endValue - event.startValue) * config.slotScale
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
    event: Event,
    eventSlot: Slot,
    dailyAgendaState: DailyAgendaState
): Int {
    val containingSlots = getSlotsIncludeStartSlot(event, eventSlot, dailyAgendaState.slots)
    val maxNumberOfEvents = containingSlots.fold(initial = 0) { maxNumberOfEvents, slot ->
        val numberOfEvents = dailyAgendaState.slotInfoMap[slot]?.getTotalColumnSpans() ?: 0
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
    event: Event,
    eventSlot: Slot,
    slots: List<Slot>
): List<Slot> {
    val slotIndex = slots.indexOf(eventSlot)
    val eventSlots = slots.subList(slotIndex, slots.size)
    val containingSlots = mutableListOf<Slot>()
    eventSlots.forEach { slot ->
        if (event.endValue > slot.startValue + 0.0001) {
            containingSlots.add(slot)
        }
    }
    return containingSlots
}

/**
 * Returns a list of the slots touched by the given event. Excluding its own start slot.
 * */
internal fun getSlotsIgnoreStartSlot(
    dailyAgendaState: DailyAgendaState,
    event: Event,
    eventSlot: Slot
): List<Slot> {
    val slotIndex = dailyAgendaState.slots.indexOf(eventSlot)
    val laterSlots = dailyAgendaState.slots.subList(slotIndex + 1, dailyAgendaState.slots.size)
    val containingSlots = mutableListOf<Slot>()
    laterSlots.forEach { slot ->
        if (event.endValue > slot.startValue + 0.0001) {
            containingSlots.add(slot)
        }
    }
    return containingSlots
}

internal fun updateEventOffsetX(
    dailyAgendaState: DailyAgendaState,
    event: Event,
    eventSlot: Slot,
    slotOffsetInfoMap: Map<Slot, OffsetInfo>,
    eventWidth: Dp,
    isLeft: Boolean
) {

    val eventSlops = getSlotsIgnoreStartSlot(
        dailyAgendaState = dailyAgendaState,
        event = event,
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

internal fun Event.isSingleSlot(eventSlot: Slot): Boolean {
    return eventSlot.endValue >= endValue
}

internal fun getEventWidthFromLeft(
    dailyAgendaState: DailyAgendaState,
    event: Event,
    eventSlot: Slot,
    amountOfEventsInSameSlot: Int,
    currentEventIndex: Int,
    eventContainerWidth: Dp,
    offsetInfo: OffsetInfo,
    slotRemainingWidth: Dp,
    minimumWidth: Dp
): Dp {
    if (shouldReturnMinimumAllowedWidth(dailyAgendaState.config, event, eventSlot)) {
        return minimumWidth
    }

    var singleSlotWidth: Dp? = null

    val eventWidth = if (event.isSingleSlot(eventSlot)) {
        singleSlotWidth ?: run {
            val amountOfSingleSlotEvents = (amountOfEventsInSameSlot - currentEventIndex)
            ((eventContainerWidth - offsetInfo.getTotalLeftOffset() - offsetInfo.rightStartOffset) / amountOfSingleSlotEvents).also {
                singleSlotWidth = it
            }
        }
    } else {
        val widthNumber = getMaximumNumberOfSiblingsInContainingSlots(
            event,
            eventSlot,
            dailyAgendaState
        )
        (slotRemainingWidth / widthNumber)
    }

    return max(minimumWidth, eventWidth)
}

internal fun getEventWidthFromRight(
    dailyAgendaState: DailyAgendaState,
    event: Event,
    eventSlot: Slot,
    amountOfEventsInSameSlot: Int,
    currentEventIndex: Int,
    eventContainerWidth: Dp,
    offsetInfo: OffsetInfo,
    slotRemainingWidth: Dp,
    minimumWidth: Dp
): Dp {
    if (shouldReturnMinimumAllowedWidth(dailyAgendaState.config, event, eventSlot)) {
        return minimumWidth
    }

    var singleSlotWidth: Dp? = null

    val eventWidth: Dp = if (event.isSingleSlot(eventSlot)) {
        singleSlotWidth ?: run {
            val amountOfSingleSlotEvents = (amountOfEventsInSameSlot - currentEventIndex)
            ((eventContainerWidth - offsetInfo.leftStartOffset - offsetInfo.getTotalRightOffset()) / amountOfSingleSlotEvents).also {
                singleSlotWidth = it
            }
        }
    } else {
        val widthNumber = getMaximumNumberOfSiblingsInContainingSlots(
            event,
            eventSlot,
            dailyAgendaState
        )
        (slotRemainingWidth / widthNumber)
    }

    return max(minimumWidth, eventWidth)
}

private fun shouldReturnMinimumAllowedWidth(
    config: Config,
    event: Event,
    eventSlot: Slot
): Boolean {
    when (val eventsArrangement = config.eventsArrangement) {
        is EventsArrangement.LeftToRight -> {
            if (!eventsArrangement.lastEventFillRow) {
                return true
            }
            if (!event.isSingleSlot(eventSlot)) {
                return true
            }
            return false
        }

        is EventsArrangement.MixedDirections -> {
            return when (eventsArrangement.eventWidthType) {
                EventWidthType.MaxVariableSize -> false
                EventWidthType.FixedSize -> true
                EventWidthType.FixedSizeFillLastEvent -> !event.isSingleSlot(eventSlot)
            }

        }

        is EventsArrangement.RightToLeft -> {
            if (!eventsArrangement.lastEventFillRow) {
                return true
            }
            if (!event.isSingleSlot(eventSlot)) {
                return true
            }
            return false
        }
    }
}
