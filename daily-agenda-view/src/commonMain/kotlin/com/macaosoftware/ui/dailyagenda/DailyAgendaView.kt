package com.macaosoftware.ui.dailyagenda

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun DailyAgendaRootLayout(
    dailyAgendaState: DailyAgendaState,
    eventContentProvider: @Composable (event: Event) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(start = dailyAgendaState.config.timelineLeftPadding.dp)
            .fillMaxSize()
    ) {

        val containerSize = LocalWindowInfo.current.containerSize
        val density = LocalDensity.current.density

        val eventContainerWidth =
            (containerSize.width.dp / density) - dailyAgendaState.config.timelineLeftPadding.dp

        LeftThenRightLayout(
            dailyAgendaState = dailyAgendaState,
            eventContainerWidth = eventContainerWidth,
            eventContentProvider = eventContentProvider
        )
    }
}

@Composable
private fun LeftThenRightLayout(
    dailyAgendaState: DailyAgendaState,
    eventContainerWidth: Dp,
    eventContentProvider: @Composable (event: Event) -> Unit
) {
    val config = dailyAgendaState.config
    val minimumWidth = eventContainerWidth / dailyAgendaState.maxColumns

    var arrangeToTheLeft = remember(key1 = dailyAgendaState, key2 = eventContainerWidth) {
        when (config.eventsArrangement) {
            is EventsArrangement.LeftToRight,
            is EventsArrangement.MixedDirections -> true

            is EventsArrangement.RightToLeft -> false
        }
    }

    val offsetInfoMap = remember(key1 = dailyAgendaState, key2 = eventContainerWidth) {
        val offsetMap = mutableMapOf<Slot, OffsetInfo>()
        for (i in 0..dailyAgendaState.slots.lastIndex) {
            offsetMap.put(dailyAgendaState.slots[i], OffsetInfo())
        }
        offsetMap
    }

    dailyAgendaState.slotToEventMap.entries.forEach { entry ->
        val slot = entry.key
        val numbersOfSlots = (slot.value - config.initialSlotValue) * config.slotScale
        val offsetY = (numbersOfSlots * config.slotHeight).dp

        var offsetXAbsolute: Dp = 0.dp
        var offsetInfo: OffsetInfo
        val offsetX = if (arrangeToTheLeft) {
            offsetInfo = offsetInfoMap[slot] ?: OffsetInfo()
            offsetXAbsolute = offsetInfo.getTotalLeftOffset()
            offsetXAbsolute
        } else {
            offsetInfo = offsetInfoMap[slot] ?: OffsetInfo()
            offsetXAbsolute = offsetInfo.getTotalRightOffset()
            -offsetXAbsolute
        }

        val slotRemainingWidth = eventContainerWidth - offsetXAbsolute

        if (arrangeToTheLeft) {
            Row(
                modifier = Modifier
                    .offset(y = offsetY, x = offsetX)
                    .wrapContentSize()
            ) {
                entry.value.forEachIndexed { idx, event ->
                    val eventTranslation = getEventTranslationInSlot(event, slot, config)
                    val eventHeight = getEventHeight(event, config)
                    val eventWidth = getEventWidthFromLeft(
                        dailyAgendaState = dailyAgendaState,
                        event = event,
                        eventSlot = slot,
                        amountOfEventsInSameSlot = entry.value.size,
                        currentEventIndex = idx,
                        eventContainerWidth = eventContainerWidth,
                        offsetInfo = offsetInfo,
                        slotRemainingWidth = slotRemainingWidth,
                        minimumWidth = minimumWidth
                    )
                    updateEventOffsetX(
                        dailyAgendaState = dailyAgendaState,
                        event = event,
                        eventSlot = slot,
                        slotOffsetInfoMap = offsetInfoMap,
                        eventWidth = eventWidth,
                        isLeft = true
                    )
                    Box(
                        modifier = Modifier
                            .offset(y = eventTranslation)
                            .height(height = eventHeight)
                            .width(width = eventWidth)
                    ) { eventContentProvider.invoke(event) }
                }
            }

        } else {
            RtlCustomRow(
                modifier = Modifier
                    .offset(y = offsetY, x = offsetX)
                    .wrapContentSize(),
            ) {
                entry.value.forEachIndexed { idx, event ->
                    val eventTranslation = getEventTranslationInSlot(event, slot, config)
                    val eventHeight = getEventHeight(event, config)
                    val eventWidth = getEventWidthFromRight(
                        dailyAgendaState = dailyAgendaState,
                        event = event,
                        eventSlot = slot,
                        amountOfEventsInSameSlot = entry.value.size,
                        currentEventIndex = idx,
                        eventContainerWidth = eventContainerWidth,
                        offsetInfo = offsetInfo,
                        slotRemainingWidth = slotRemainingWidth,
                        minimumWidth = minimumWidth
                    )
                    updateEventOffsetX(
                        dailyAgendaState = dailyAgendaState,
                        event = event,
                        eventSlot = slot,
                        slotOffsetInfoMap = offsetInfoMap,
                        eventWidth = eventWidth,
                        isLeft = false
                    )
                    Box(
                        modifier = Modifier
                            .offset(y = eventTranslation)
                            .height(height = eventHeight)
                            .width(width = eventWidth)
                    ) { eventContentProvider.invoke(event) }
                }
            }
        }

        if (dailyAgendaState.config.eventsArrangement is EventsArrangement.MixedDirections) {
            arrangeToTheLeft = !arrangeToTheLeft
        }
    }
}
