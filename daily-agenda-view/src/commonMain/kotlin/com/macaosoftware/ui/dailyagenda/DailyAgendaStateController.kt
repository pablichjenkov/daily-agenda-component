package com.macaosoftware.ui.dailyagenda

import androidx.compose.runtime.mutableStateOf

class DailyAgendaStateController(
    val slots: List<Slot>,
    val slotToEventMap: Map<Slot, List<Event>>,
    val config: Config
) {

    val state = mutableStateOf<DailyAgendaState?>(null)

    fun start() {

        val result = computeSlotInfo(
            slots = slots,
            slotToEventMap = slotToEventMap,
            config = config
        )

        state.value = DailyAgendaState(
            slots = slots,
            slotToEventMap = slotToEventMap,
            slotInfoMap = result.slotInfoMap,
            maxColumns = result.maxColumns,
            config = config
        )
    }

    /**
     * Computes the amount of events that are contained by the given slop. The events will be the
     * sum of earlier slots events plus this slot's events.
     * */
    private fun computeSlotInfo(
        slots: List<Slot>,
        slotToEventMap: Map<Slot, List<Event>>,
        config: Config
    ): ComputeSlotInfoResult {

        val slotInfoMap = mutableMapOf<Slot, SlotInfo>()
        var maxColumns = 1

        var isLeftIter = when (config) {
            is Config.LeftToRight,
            is Config.MixedDirections -> true

            is Config.RightToLeft -> false
        }

        slots.forEachIndexed { idx, slotIter ->

            val slotLeftColumnMap = mutableMapOf<Slot, Int>()
            val slotRightColumnMap = mutableMapOf<Slot, Int>()

            slotToEventMap[slotIter]?.forEachIndexed { idx, event ->

                getSlotsIncludeStartSlot(event, slots).forEach { containingSlot ->
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


            println("DailyAgendaState: ============== Slot: ${slotIter.title} ================")
            slotLeftColumnMap.entries.forEach {
                println("DailyAgendaState: slotLeftColumnMap: ${it.key.title}, ${it.value}")
            }
            println("DailyAgendaState: +++++++++++++++ slotInfoMap: +++++++++++++++")
            slotInfoMap.entries.forEach {
                println("DailyAgendaState: SlotInfoMap: ${it.key.title}, ${it.value}")
            }

            if (config is Config.MixedDirections) {
                isLeftIter = !isLeftIter
            }
        }

        slotInfoMap.entries.fold(1) { acc, entry ->
            val slotColumns = entry.value.getTotalColumnSpans()
            if (slotColumns > maxColumns) maxColumns = slotColumns
            println("DailyAgendaState: SlotInfoMap: ${entry.value}")
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
