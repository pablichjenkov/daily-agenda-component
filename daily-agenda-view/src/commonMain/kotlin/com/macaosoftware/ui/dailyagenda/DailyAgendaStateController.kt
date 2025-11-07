package com.macaosoftware.ui.dailyagenda

import androidx.compose.runtime.mutableStateOf

class DailyAgendaStateController(
    private val slots: List<Slot>,
    slotToEventMap: Map<Slot, List<Event>>,
    private val config: Config
) {

    private val slotToEventMapSorted: MutableMap<Slot, MutableList<Event>> = mutableMapOf()
    val state = mutableStateOf<DailyAgendaState?>(null)

    init {
        /**
         * Sort the events to maximize spacing when the layout runs.
         * */
        val endTimeComparator = Comparator { event1: Event, event2: Event ->
            (event2.endTime - event1.endTime).toInt()
        }
        slotToEventMap.entries.forEach { entry ->
            val eventsSortedByEndTime = entry.value.sortedWith(endTimeComparator).toMutableList()
            slotToEventMapSorted.put(entry.key, eventsSortedByEndTime)
        }
        updateState()
    }

    fun addEvent(event: Event): Boolean {
        val siblingEvents = slotToEventMapSorted[event.startSlot]?.toMutableList() ?: return false

        val index = siblingEvents.binarySearch(fromIndex = 0, toIndex = siblingEvents.lastIndex) {
            (event.endTime - it.endTime).toInt()
        }
        val insertionIndex = if (index < 0) -(index + 1) else index
        siblingEvents.add(insertionIndex, event)

        // Update state
        updateState()
        return true
    }

    fun removeEvent(event: Event): Boolean {
        val siblingEvents = slotToEventMapSorted[event.startSlot]?.toMutableList() ?: return false
        return if (siblingEvents.remove(event)) { // Update only if removal was success
            updateState()
            true
        } else false
    }

    private fun updateState() {
        // Precompute some measurement and layout info aot for performance
        val result = computeSlotInfo(
            slots = slots,
            slotToEventMap = slotToEventMapSorted,
            config = config
        )
        state.value = DailyAgendaState(
            slots = slots,
            slotToEventMap = slotToEventMapSorted,
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
