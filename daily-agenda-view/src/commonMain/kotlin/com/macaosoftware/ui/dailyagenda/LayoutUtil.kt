package com.macaosoftware.ui.dailyagenda

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max

/**
 * Returns the Event Y axis offset in Dp from the slot start time.
 * */
fun getEventTranslationInSlot(event: Event, config: Config): Dp {
    val fractionOfSlots = (event.startTime - event.startSlot.time) / config.slotUnit
    return (fractionOfSlots * config.slotHeight).dp
}

/**
 * Returns the height in Dp for a given Event based on the amount of slots it touches.
 * */
fun getEventHeight(event: Event, config: Config): Dp {
    val numberOfSlots = (event.endTime - event.startTime) / config.slotUnit
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
    dailyAgendaState: DailyAgendaState
): Int {
    val containingSlots = getSlotsIncludeStartSlot(event, dailyAgendaState.slots)
    val maxNumberOfEvents = containingSlots.fold(initial = 1) { maxNumberOfEvents, slot ->
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
fun getSlotsIncludeStartSlot(
    event: Event,
    slots: List<Slot>
): List<Slot> {
    val slotIndex = slots.indexOf(event.startSlot)
    val eventSlots = slots.subList(slotIndex, slots.size)
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
    dailyAgendaState: DailyAgendaState,
    event: Event
): List<Slot> {
    val slotIndex = dailyAgendaState.slots.indexOf(event.startSlot)
    val laterSlots = dailyAgendaState.slots.subList(slotIndex + 1, dailyAgendaState.slots.size)
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
    dailyAgendaState: DailyAgendaState,
    event: Event,
    slotOffsetInfoMap: Map<Slot, OffsetInfo>,
    eventWidth: Dp,
    isLeft: Boolean
) {

    val eventSlops = getSlotsIgnoreStartSlot(
        dailyAgendaState = dailyAgendaState,
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

internal fun Event.isSingleSlot(): Boolean {
    return endTime - startTime < 0.6
}

internal fun getEventWidthFromLeft(
    dailyAgendaState: DailyAgendaState,
    event: Event,
    amountOfEventsInSameSlot: Int,
    currentEventIndex: Int,
    eventContainerWidth: Dp,
    offsetInfo: OffsetInfo,
    slotRemainingWidth: Dp,
    minimumWidth: Dp
): Dp {
    if (shouldReturnMinimumAllowedWidth(dailyAgendaState.config, event)) {
        return minimumWidth
    }

    var singleSlotWidth: Dp? = null

    val eventWidth = if (event.isSingleSlot()) {
        singleSlotWidth ?: run {
            val amountOfSingleSlotEvents = (amountOfEventsInSameSlot - currentEventIndex)
            ((eventContainerWidth - offsetInfo.getTotalLeftOffset() - offsetInfo.rightStartOffset) / amountOfSingleSlotEvents).also {
                singleSlotWidth = it
            }
        }
    } else {
        val widthNumber = getMaximumNumberOfSiblingsInContainingSlots(
            event,
            dailyAgendaState
        )
        (slotRemainingWidth / widthNumber)
    }

    return max(minimumWidth, eventWidth)
}

internal fun getEventWidthFromRight(
    dailyAgendaState: DailyAgendaState,
    event: Event,
    amountOfEventsInSameSlot: Int,
    currentEventIndex: Int,
    eventContainerWidth: Dp,
    offsetInfo: OffsetInfo,
    slotRemainingWidth: Dp,
    minimumWidth: Dp
): Dp {
    if (shouldReturnMinimumAllowedWidth(dailyAgendaState.config, event)) {
        return minimumWidth
    }

    var singleSlotWidth: Dp? = null

    val eventWidth: Dp = if (event.isSingleSlot()) {
        singleSlotWidth ?: run {
            val amountOfSingleSlotEvents = (amountOfEventsInSameSlot - currentEventIndex)
            ((eventContainerWidth - offsetInfo.leftStartOffset - offsetInfo.getTotalRightOffset()) / amountOfSingleSlotEvents).also {
                singleSlotWidth = it
            }
        }
    } else {
        val widthNumber = getMaximumNumberOfSiblingsInContainingSlots(
            event,
            dailyAgendaState
        )
        (slotRemainingWidth / widthNumber)
    }

    return max(minimumWidth, eventWidth)
}

private fun shouldReturnMinimumAllowedWidth(
    config: Config,
    event: Event
): Boolean {
    when (val config = config) {
        is Config.LeftToRight -> {
            if (!config.lastEventFillRow) {
                return true
            }
            if (!event.isSingleSlot()) {
                return true
            }
            return false
        }

        is Config.MixedDirections -> {
            return when (config.eventWidthType) {
                EventWidthType.MaxVariableSize -> false
                EventWidthType.FixedSize -> true
                EventWidthType.FixedSizeFillLastEvent -> !event.isSingleSlot()
            }

        }

        is Config.RightToLeft -> {
            if (!config.lastEventFillRow) {
                return true
            }
            if (!event.isSingleSlot()) {
                return true
            }
            return false
        }
    }
}
