package com.rcl.excalibur.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rcl.excalibur.calendar.ui.theme.CalendarTheme
import kotlin.random.Random

@Composable
fun CalendarView(calendarState: CalendarState) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Box {
            SlotsLayer(calendarState = calendarState)
            Box(
                modifier = Modifier
                    .padding(start = TimeTitleLeftPadding.dp) // TODO: Compute this value properly. It is the slot.title width
                    .fillMaxSize()
            ) {

                val containerSize = LocalWindowInfo.current.containerSize
                val density = LocalDensity.current.density

                val eventContainerWidth =
                    (containerSize.width.dp / density) - TimeTitleLeftPadding.dp

                var arrangeToTheLeft = remember { true }

                val offsetInfoMap = remember {
                    mapOf<Slot, OffsetInfo>(
                        calendarState.slots[0] to OffsetInfo(),
                        calendarState.slots[1] to OffsetInfo(),
                        calendarState.slots[2] to OffsetInfo(),
                        calendarState.slots[3] to OffsetInfo(),
                        calendarState.slots[4] to OffsetInfo(),
                        calendarState.slots[5] to OffsetInfo(),
                        calendarState.slots[6] to OffsetInfo(),
                        calendarState.slots[7] to OffsetInfo(),
                        calendarState.slots[8] to OffsetInfo(),
                        calendarState.slots[9] to OffsetInfo(),
                        calendarState.slots[10] to OffsetInfo(),
                        calendarState.slots[11] to OffsetInfo(),
                        calendarState.slots[12] to OffsetInfo(),
                        calendarState.slots[13] to OffsetInfo(),
                        calendarState.slots[14] to OffsetInfo(),
                        calendarState.slots[15] to OffsetInfo(),
                        calendarState.slots[16] to OffsetInfo(),
                        // TODO: Generate this programmatically
                    )
                }

                calendarState.slotToEventMap.entries.forEach { entry ->
                    val slot = entry.key
                    val numbersOfSlots = (slot.time - 8.0F) / 0.5F
                    val offsetY = (numbersOfSlots * SlotHeight).dp

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
                        println("MainActivity: Adding new LtrRow with offsetY: $offsetY, offsetX: $offsetX")
                        Row(
                            modifier = Modifier
                                .offset(y = offsetY, x = offsetX)
                                .wrapContentSize()
                        ) {

                            var singleSlotWidth: Dp? = null
                            entry.value.forEachIndexed { idx, event ->

                                val eventHeight = getEventHeight(event)

                                val eventWidth = if (event.isSingleSlot()) {
                                    singleSlotWidth ?: run {
                                        val amountOfSingleSlotEvents = (entry.value.size - idx)
                                        ((eventContainerWidth - offsetInfo.getTotalLeftOffset() - offsetInfo.rightStartOffset) / amountOfSingleSlotEvents).also {
                                            singleSlotWidth = it
                                        }
                                    }
                                } else {
                                    val widthNumber = getEventWidth(event, slot, calendarState)
                                    (slotRemainingWidth / widthNumber)
                                }

                                updateEventOffsetX(
                                    calendarState = calendarState,
                                    event = event,
                                    slotOffsetInfoMap = offsetInfoMap,
                                    eventWidth = eventWidth,
                                    isLeft = true
                                )

                                println("MainActivity: Adding Box in LTR with height: $eventHeight, width: $eventWidth")

                                Column(
                                    modifier = Modifier
                                        .height(height = eventHeight)
                                        .width(width = eventWidth)
                                        .background(color = generateRandomColor())
                                ) {
                                    Text(text = "${event.title}: ${event.startTime}-${event.endTime}")
                                }
                            }
                        }

                    } else {
                        println("MainActivity: Adding new RtlRow with offsetY: $offsetY, offsetX: $offsetX")
                        RtlCustomRow(
                            modifier = Modifier
                                .offset(y = offsetY, x = offsetX)
                                .wrapContentSize(),
                        ) {

                            var singleSlotWidth: Dp? = null
                            entry.value.forEachIndexed { idx, event ->

                                val eventHeight = getEventHeight(event)

                                val eventWidth: Dp = if (event.isSingleSlot()) {
                                    singleSlotWidth ?: run {
                                        val amountOfSingleSlotEvents = (entry.value.size - idx)
                                        ((eventContainerWidth - offsetInfo.leftStartOffset - offsetInfo.getTotalRightOffset()) / amountOfSingleSlotEvents).also {
                                            singleSlotWidth = it
                                        }
                                    }
                                } else {
                                    val widthNumber = getEventWidth(event, slot, calendarState)
                                    (slotRemainingWidth / widthNumber)
                                }

                                updateEventOffsetX(
                                    calendarState = calendarState,
                                    event = event,
                                    slotOffsetInfoMap = offsetInfoMap,
                                    eventWidth = eventWidth,
                                    isLeft = false
                                )

                                println("MainActivity: Adding Box in RTL with height: $eventHeight, width: $eventWidth")

                                Column(
                                    modifier = Modifier
                                        .height(height = eventHeight)
                                        .width(width = eventWidth)
                                        .background(color = generateRandomColor())
                                ) {
                                    Text(text = "${event.title}: ${event.startTime}-${event.endTime}")
                                }
                            }
                        }
                    }

                    arrangeToTheLeft = !arrangeToTheLeft
                }
            }
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

@Preview(
    device = "spec:width=411dp,height=891dp,dpi=420",
    showBackground = true
)
@Composable
fun CalendarViewPreview() {

    val calendarState = remember { CalendarState() }
    CalendarTheme {
        Box(modifier = Modifier.size(600.dp, 1000.dp)) {
            CalendarView(
                calendarState = calendarState
            )
        }
    }
}
