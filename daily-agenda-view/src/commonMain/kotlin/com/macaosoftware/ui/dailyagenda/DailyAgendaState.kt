package com.macaosoftware.ui.dailyagenda

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class DailyAgendaState(
    val slots: List<Slot>,
    val slotToEventMap: Map<Slot, List<Event>>,
    val slotInfoMap: Map<Slot, SlotInfo>,
    val maxColumns: Int,
    val config: Config
)

data class Slot(
    val title: String,
    val time: Float,
    val index: Int
)

// TODO: Change var with val SlotInfo.copy()
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

sealed class Config(
    open val initialSlotValue: Float = 0.0F,
    open val slotScale: Float = 1F,
    open val slotHeight: Int = 72,
    open val timelineLeftPadding: Int = 72
) {

    class MixedDirections(
        val eventWidthType: EventWidthType = EventWidthType.MaxVariableSize,
        override val initialSlotValue: Float,
        override val slotScale: Float,
        override val slotHeight: Int,
        override val timelineLeftPadding: Int
    ) : Config()

    class LeftToRight(
        val lastEventFillRow: Boolean = true,
        override val initialSlotValue: Float,
        override val slotScale: Float,
        override val slotHeight: Int,
        override val timelineLeftPadding: Int
    ) : Config()

    class RightToLeft(
        val lastEventFillRow: Boolean = true,
        override val initialSlotValue: Float,
        override val slotScale: Float,
        override val slotHeight: Int,
        override val timelineLeftPadding: Int
    ) : Config()
}

enum class EventWidthType { MaxVariableSize, FixedSize, FixedSizeFillLastEvent }
