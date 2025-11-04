package com.macaosoftware.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.macaosoftware.ui.Config.Companion.defaultValue

class DailyAgendaState(
    val slots: List<Slot>,
    val slotToEventMap: Map<Slot, List<Event>>,
    val slotInfoMap: Map<Slot, SlotInfo>,
    val maxColumns: Int,
    val config: Config
)

class Slot(
    val title: String,
    val time: Float, // 8.0, 8.5, 9.0, 9.5, 10.0 ... 23.5, 24.0
)

// TODO: Change var with val
data class SlotInfo(
    var numberOfContainingEvents: Int,
    var numberOfColumnsLeft: Int,
    var numberOfColumnsRight: Int,
) {
    fun getTotalColumnSpans() = numberOfColumnsLeft + numberOfColumnsRight
}

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

sealed class Config{

    class MixedDirections(
        val eventWidthType: EventWidthType = EventWidthType.VariableSize
    ) : Config()

    class LeftToRight(
        val lastEventFillRow: Boolean = true
    ) : Config()

    class RightToLeft(
        val lastEventFillRow: Boolean = true
    ) : Config()

    companion object {
        fun defaultValue(): Config = MixedDirections()
    }
}

enum class EventWidthType { VariableSize, FixedSize, FixedSizeFillLastEvent }
