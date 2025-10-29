package com.rcl.excalibur.calendar

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rcl.excalibur.calendar.data.Sample0
import com.rcl.excalibur.calendar.data.Sample1
import com.rcl.excalibur.calendar.data.Sample2
import com.rcl.excalibur.calendar.data.Sample3
import com.rcl.excalibur.calendar.data.Slots

class CalendarState {
    val slots = Slots.slots
    val sampleData = Sample3(slots)
    val slotToEventMap = sampleData.slotToEventMap
    internal val slotMetadataMap = sampleData.slotMetadataMap
}

class Slot(
    val title: String,
    val time: Float, // 8.0, 8.5, 9.0, 9.5, 10.0 ... 23.5, 24.0
)

class Event(
    val startSlot: Slot,
    val title: String,
    val startTime: Float,
    val endTime: Float
)

internal class OffsetInfo(
    var leftStartOffset: Dp = 0.dp,
    var leftAccumulated: Dp = 0.dp,
    var rightStartOffset: Dp = 0.dp,
    var rightAccumulated: Dp = 0.dp
) {
    fun getTotalLeftOffset() = leftStartOffset + leftAccumulated

    fun getTotalRightOffset() = rightStartOffset + rightAccumulated
}
