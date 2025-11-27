package com.macaosoftware.ui.dailyagenda.decimalslots

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

internal data class DecimalSlotsBaseLayoutState(
    val slots: List<Slot>,
    val slotToDecimalEventMap: Map<Slot, List<DecimalEvent>>,
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

@OptIn(ExperimentalUuidApi::class)
data class DecimalEvent(
    val uuid: Uuid,
    val title: String,
    val description: String,
    val startValue: Float,
    val endValue: Float
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DecimalEvent) return false
        return other.uuid == uuid
    }

    override fun hashCode(): Int {
        return uuid.hashCode()
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

data class DecimalSlotConfig(
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
