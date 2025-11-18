package com.macaosoftware.ui.dailyagenda

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class DailyAgendaState(
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
    val title: String,
    val startValue: Float,
    val endValue: Float
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherEvent: Event = (other as? Event) ?: return false
        if (otherEvent.startValue != startValue) return false
        if (otherEvent.endValue != endValue) return false
        return otherEvent.title == title
    }

    override fun hashCode(): Int {
        var result = startValue.hashCode()
        result = 31 * result + endValue.hashCode()
        result = 31 * result + title.hashCode()
        return result
    }
}

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

@ConsistentCopyVisibility
data class Config internal constructor(
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
