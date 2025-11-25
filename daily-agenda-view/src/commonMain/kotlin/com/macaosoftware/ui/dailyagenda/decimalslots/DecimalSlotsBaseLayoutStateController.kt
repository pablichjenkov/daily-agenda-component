package com.macaosoftware.ui.dailyagenda.decimalslots

import androidx.compose.runtime.mutableStateOf
import kotlin.math.abs

class DecimalSlotsBaseLayoutStateController(
    val slots: List<Slot>,
    slotConfig: SlotConfig,
    private val eventsArrangement: EventsArrangement
) {

    val slotUnit = 1.0F / slotConfig.slotScale

    private val config = Config(
        eventsArrangement = eventsArrangement,
        initialSlotValue = slots[0].startValue,
        lastSlotValue = slots[slots.lastIndex].startValue,
        slotScale = slotConfig.slotScale,
        slotHeight = slotConfig.slotHeight,
        timelineLeftPadding = 72
    )

    internal val slotToDecimalEventMapSorted: MutableMap<Slot, MutableList<DecimalEvent>> = mutableMapOf()

    val state = mutableStateOf<DecimalSlotsBaseLayoutState>(value = computeNextState())

    init {
        slots.forEach { slotToDecimalEventMapSorted[it] = mutableListOf() }
    }

    fun getSlotForValue(startValue: Float): Slot {
        return slots.find { abs(x = startValue - it.startValue) < slotUnit }
            ?: error("startTime: $startValue must be between ${config.initialSlotValue} and ${config.lastSlotValue}")
    }

    internal fun computeNextState(): DecimalSlotsBaseLayoutState {
        val result = computeSlotInfo(
            slots = slots,
            slotToDecimalEventMap = slotToDecimalEventMapSorted,
            config = config
        )
        return DecimalSlotsBaseLayoutState(
            slots = slots,
            slotToDecimalEventMap = slotToDecimalEventMapSorted,
            slotInfoMap = result.slotInfoMap,
            maxColumns = result.maxColumns,
            config = config
        )
    }

    internal fun updateState() {
        state.value = computeNextState()
    }

    /**
     * Computes the amount of events that are contained by the given slop. The events will be the
     * sum of earlier slots events plus this slot's events.
     * */
    private fun computeSlotInfo(
        slots: List<Slot>,
        slotToDecimalEventMap: Map<Slot, List<DecimalEvent>>,
        config: Config
    ): ComputeSlotInfoResult {

        val slotInfoMap = mutableMapOf<Slot, SlotInfo>()
        var maxColumns = 1

        var isLeftIter = when (config.eventsArrangement) {
            is EventsArrangement.LeftToRight,
            is EventsArrangement.MixedDirections -> true

            is EventsArrangement.RightToLeft -> false
        }

        slots.forEachIndexed { idx, slotIter ->

            val slotLeftColumnMap = mutableMapOf<Slot, Int>()
            val slotRightColumnMap = mutableMapOf<Slot, Int>()

            slotToDecimalEventMap[slotIter]?.forEachIndexed { idx, event ->

                val eventSlot = getSlotForValue(startValue = event.startValue)

                getSlotsIncludeStartSlot(event, eventSlot, slots).forEach { containingSlot ->
                    // Update column mark
                    if (isLeftIter) {
                        slotLeftColumnMap.put(containingSlot, idx + 1)
                    } else {
                        slotRightColumnMap.put(containingSlot, idx + 1)
                    }

                    // Update events counter
                    val currentSlotInfo =
                        slotInfoMap.getOrPut(containingSlot) { SlotInfo(0, 0, 0) }
                    currentSlotInfo.numberOfContainingEvents++
                }
            }

            val iterSlotInfo = slotInfoMap.getOrPut(slotIter) { SlotInfo(0, 0, 0) }
            if (isLeftIter) {
                slotLeftColumnMap.entries.forEach { entry ->
                    if (entry.key.title != slotIter.title) {
                        val entrySlotInfo = slotInfoMap.getOrPut(entry.key) { SlotInfo(0, 0, 0) }
                        entrySlotInfo.numberOfColumnsLeft =
                            iterSlotInfo.numberOfColumnsLeft + entry.value
                    }
                }
                iterSlotInfo.numberOfColumnsLeft += slotLeftColumnMap.entries.firstOrNull()?.value
                    ?: 0
            } else {
                slotRightColumnMap.entries.forEach { entry ->
                    if (entry.key.title != slotIter.title) {
                        val entrySlotInfo = slotInfoMap.getOrPut(entry.key) { SlotInfo(0, 0, 0) }
                        entrySlotInfo.numberOfColumnsRight =
                            iterSlotInfo.numberOfColumnsRight + entry.value
                    }
                }
                iterSlotInfo.numberOfColumnsRight += slotRightColumnMap.entries.firstOrNull()?.value
                    ?: 0
            }

            // In the case of eventsArrangement == EventsArrangement.MixedDirections, then
            // lets change the layout direction.
            if (config.eventsArrangement is EventsArrangement.MixedDirections) {
                isLeftIter = !isLeftIter
            }
        }

        slotInfoMap.entries.fold(1) { acc, entry ->
            val slotColumns = entry.value.getTotalColumnSpans()
            if (slotColumns > maxColumns) maxColumns = slotColumns
            maxColumns
        }
        println("DailyAgendaState: maxColumns: $maxColumns")
        return ComputeSlotInfoResult(slotInfoMap, maxColumns)
    }

}

private class ComputeSlotInfoResult(
    val slotInfoMap: Map<Slot, SlotInfo>,
    val maxColumns: Int
)
