package com.macaosoftware.ui.dailyagenda

import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun DailyAgendaView(dailyAgendaState: DailyAgendaState) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Box {
            SlotsLayer(dailyAgendaState = dailyAgendaState)
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
                    eventContainerWidth = eventContainerWidth
                )
            }
        }
    }
}

@Composable
private fun LeftThenRightLayout(
    dailyAgendaState: DailyAgendaState,
    eventContainerWidth: Dp
) {
    val config = dailyAgendaState.config
    val minimumWidth = eventContainerWidth / dailyAgendaState.maxColumns

    var arrangeToTheLeft = remember(key1 = dailyAgendaState, key2 = eventContainerWidth) {
        when (config) {
            is Config.LeftToRight,
            is Config.MixedDirections -> true

            is Config.RightToLeft -> false
        }
    }

    val offsetInfoMap = remember(key1 = dailyAgendaState, key2 = eventContainerWidth) {
        mapOf<Slot, OffsetInfo>(
            dailyAgendaState.slots[0] to OffsetInfo(),
            dailyAgendaState.slots[1] to OffsetInfo(),
            dailyAgendaState.slots[2] to OffsetInfo(),
            dailyAgendaState.slots[3] to OffsetInfo(),
            dailyAgendaState.slots[4] to OffsetInfo(),
            dailyAgendaState.slots[5] to OffsetInfo(),
            dailyAgendaState.slots[6] to OffsetInfo(),
            dailyAgendaState.slots[7] to OffsetInfo(),
            dailyAgendaState.slots[8] to OffsetInfo(),
            dailyAgendaState.slots[9] to OffsetInfo(),
            dailyAgendaState.slots[10] to OffsetInfo(),
            dailyAgendaState.slots[11] to OffsetInfo(),
            dailyAgendaState.slots[12] to OffsetInfo(),
            dailyAgendaState.slots[13] to OffsetInfo(),
            dailyAgendaState.slots[14] to OffsetInfo(),
            dailyAgendaState.slots[15] to OffsetInfo(),
            dailyAgendaState.slots[16] to OffsetInfo(),
            dailyAgendaState.slots[17] to OffsetInfo(),
            dailyAgendaState.slots[18] to OffsetInfo(),
            dailyAgendaState.slots[19] to OffsetInfo(),
            dailyAgendaState.slots[20] to OffsetInfo(),
            // TODO: Generate this programmatically
        )
    }

    dailyAgendaState.slotToEventMap.entries.forEach { entry ->
        val slot = entry.key
        val numbersOfSlots = (slot.time - config.initialSlotValue) / config.slotUnit
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
                    val eventTranslation = getEventTranslationInSlot(event, config)
                    val eventHeight = getEventHeight(event, config)
                    val eventWidth = getEventWidthFromLeft(
                        dailyAgendaState = dailyAgendaState,
                        event = event,
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
                        slotOffsetInfoMap = offsetInfoMap,
                        eventWidth = eventWidth,
                        isLeft = true
                    )
                    Box(
                        modifier = Modifier
                            .offset(y = eventTranslation)
                            .height(height = eventHeight)
                            .width(width = eventWidth)
                            .padding(2.dp)
                            .padding(top = 1.dp)
                            .background(color = generateRandomColor())
                    ) {
                        Text(text = "${event.title}: ${event.startTime}-${event.endTime}")
                    }
                }
            }

        } else {
            RtlCustomRow(
                modifier = Modifier
                    .offset(y = offsetY, x = offsetX)
                    .wrapContentSize(),
            ) {
                entry.value.forEachIndexed { idx, event ->
                    val eventTranslation = getEventTranslationInSlot(event, config)
                    val eventHeight = getEventHeight(event, config)
                    val eventWidth = getEventWidthFromRight(
                        dailyAgendaState = dailyAgendaState,
                        event = event,
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
                        slotOffsetInfoMap = offsetInfoMap,
                        eventWidth = eventWidth,
                        isLeft = false
                    )
                    Box(
                        modifier = Modifier
                            .offset(y = eventTranslation)
                            .height(height = eventHeight)
                            .width(width = eventWidth)
                            .padding(2.dp)
                            .padding(top = 1.dp)
                            .background(color = generateRandomColor())
                    ) {
                        Text(text = "${event.title}: ${event.startTime}-${event.endTime}")
                    }
                }
            }
        }

        if (dailyAgendaState.config is Config.MixedDirections) {
            arrangeToTheLeft = !arrangeToTheLeft
        }
    }
}

fun generateRandomColor(): Color {
    val red =
        Random.nextInt(256) // Generates a random integer between 0 (inclusive) and 256 (exclusive)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)
    return Color(red, green, blue)
}
