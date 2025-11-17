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

@ConsistentCopyVisibility
data class Slot internal constructor(
    val title: String,
    val startValue: Float,
    val endValue: Float
)

// TODO: Change var with val SlotInfo.copy()
@ConsistentCopyVisibility
data class SlotInfo internal constructor(
    var numberOfContainingEvents: Int,
    var numberOfColumnsLeft: Int,
    var numberOfColumnsRight: Int,
) {
    fun getTotalColumnSpans() = numberOfColumnsLeft + numberOfColumnsRight
}

data class Event(
    // val startSlot: Slot,
    val title: String,
    val startValue: Float,
    val endValue: Float
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

data class SlotConfig(
    val initialSlotValue: Float = 0.0F,
    val lastSlotValue: Float = 20.0F,
    val slotScale: Int = 2,
    val slotHeight: Int = 64,
    val timelineLeftPadding: Int = 72
)

data class Config(
    val eventsArrangement: EventsArrangement = EventsArrangement.MixedDirections(),
    val initialSlotValue: Float,
    val lastSlotValue: Float,
    val slotScale: Int,
    val slotHeight: Int,
    val timelineLeftPadding: Int
)

sealed interface EventsArrangement {

    class MixedDirections(
        val eventWidthType: EventWidthType = EventWidthType.FixedSizeFillLastEvent,
    ) : EventsArrangement

    class LeftToRight(
        val lastEventFillRow: Boolean = true
    ) : EventsArrangement

    class RightToLeft(
        val lastEventFillRow: Boolean = true
    ) : EventsArrangement
}

enum class EventWidthType { MaxVariableSize, FixedSize, FixedSizeFillLastEvent }
