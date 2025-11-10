package com.macaosoftware.ui.dailyagenda

import androidx.compose.runtime.mutableStateOf

class DailyAgendaStateController(
    private val slotsGenerator: SlotsGenerator,
    slotToEventMap: Map<Slot, List<Event>>,
    private val config: Config
) {

    private val slots = slotsGenerator.slots
    private val slotToEventMapSorted: MutableMap<Slot, MutableList<Event>> = mutableMapOf()

    // TODO: Make this non nullable by selecting a good default value
    val state = mutableStateOf<DailyAgendaState>(value = computeNextState())

    init {
        /**
         * Sort the events to maximize spacing when the layout runs.
         * */
        val endTimeComparator = Comparator { event1: Event, event2: Event ->
            val diff = event2.endTime - event1.endTime
            when {
                (diff > 0F) -> 1
                (diff < 0F) -> -1
                else -> 0
            }
        }
        slotToEventMap.entries.forEach { entry ->
            val eventsSortedByEndTime = entry.value.sortedWith(endTimeComparator).toMutableList()
            slotToEventMapSorted.put(entry.key, eventsSortedByEndTime)
        }
        updateState()
    }

    fun addEvent(
        startTime: Float,
        endTime: Float,
        title: String
    ): Boolean {

        val startSlot = slotsGenerator.getSlotForTime(startTime)
        val siblingEvents = slotToEventMapSorted[startSlot]?.toMutableList() ?: return false

        val index = siblingEvents.binarySearch(fromIndex = 0, toIndex = siblingEvents.lastIndex) {
            val diff = it.endTime - endTime
            when {
                (diff > 0F) -> 1
                (diff < 0F) -> -1
                else -> 0
            }
        }
        val insertionIndex = if (index < 0) -(index + 1) else index
        siblingEvents.add(
            insertionIndex,
            Event(
                startSlot = startSlot,
                startTime = startTime,
                endTime = endTime,
                title = title
            )
        )

        // Update state
        updateState()
        return true
    }

    fun addEvent(event: Event): Boolean {
        val siblingEvents = slotToEventMapSorted[event.startSlot]?.toMutableList() ?: return false

        val index = siblingEvents.binarySearch(fromIndex = 0, toIndex = siblingEvents.lastIndex) {
            val diff = it.endTime - event.endTime
            when {
                (diff > 0F) -> 1
                (diff < 0F) -> -1
                else -> 0
            }
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

    private fun computeNextState(): DailyAgendaState {
        val result = computeSlotInfo(
            slots = slots,
            slotToEventMap = slotToEventMapSorted,
            config = config
        )
        return DailyAgendaState(
            slots = slots,
            slotToEventMap = slotToEventMapSorted,
            slotInfoMap = result.slotInfoMap,
            maxColumns = result.maxColumns,
            config = config
        )
    }

    private fun updateState() {
        state.value = computeNextState()
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
