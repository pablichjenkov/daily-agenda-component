package com.macaosoftware.ui.dailyagenda

open class DecimalSlotsDataUpdater(
    private val decimalSlotsStateController: DecimalSlotsStateController
) {

    private var isListOperation = false
    private val slotToEventMapSortedTemp: MutableMap<Slot, MutableList<Event>> = mutableMapOf()

    fun addEvent(
        startValue: Float,
        endValue: Float,
        title: String
    ): Boolean {
        val eventSlot = decimalSlotsStateController.getSlotForValue(startValue = startValue)
        val siblingEvents =
            decimalSlotsStateController.slotToEventMapSorted[eventSlot]?.toMutableList()
                ?: return false

        val index = siblingEvents.binarySearch(fromIndex = 0, toIndex = siblingEvents.lastIndex) {
            val diff = it.endValue - endValue
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
                startValue = startValue,
                endValue = endValue,
                title = title
            )
        )

        return true
    }

    fun addEvent(event: Event): Boolean {
        return addEvent(
            startValue = event.startValue,
            endValue = event.endValue,
            title = event.title
        )
    }

    fun addDecimalSegmentList(startValue: Float, segments: List<Event>) {
        isListOperation = true
        val slot = decimalSlotsStateController.getSlotForValue(startValue = startValue)

        if (slotToEventMapSortedTemp.contains(slot)) {
            slotToEventMapSortedTemp[slot]!!.addAll(elements = segments)
        } else {
            slotToEventMapSortedTemp.put(slot, segments.toMutableList())
        }
    }

    fun removeEvent(event: Event): Boolean {
        val eventSlot = decimalSlotsStateController.getSlotForValue(startValue = event.startValue)
        val siblingEvents =
            decimalSlotsStateController.slotToEventMapSorted[eventSlot]?.toMutableList()
                ?: return false
        return siblingEvents.remove(event)
    }

    fun commit() {

        if (isListOperation) {
            val slotToEventMapSortedMerge: MutableMap<Slot, MutableList<Event>> = mutableMapOf()

            for (slot in decimalSlotsStateController.slots) {
                val addedSegments: MutableList<Event>? = slotToEventMapSortedTemp[slot]
                val existingSegments: MutableList<Event> =
                    decimalSlotsStateController.slotToEventMapSorted[slot]!!

                if (addedSegments != null) {
                    addedSegments.addAll(existingSegments)
                    slotToEventMapSortedMerge.put(slot, addedSegments)
                } else {
                    slotToEventMapSortedMerge.put(slot, existingSegments)
                }
            }

            /**
             * Sort the events to maximize spacing when the layout runs.
             * */
            val endTimeComparator = Comparator { event1: Event, event2: Event ->
                val diff = event2.endValue - event1.endValue
                when {
                    (diff > 0F) -> 1
                    (diff < 0F) -> -1
                    else -> 0
                }
            }
            slotToEventMapSortedMerge.entries.forEach { entry ->
                val eventsSortedByEndTime = entry.value.sortedWith(endTimeComparator).toMutableList()
                decimalSlotsStateController.slotToEventMapSorted.put(
                    entry.key,
                    eventsSortedByEndTime
                )
            }
        }

        decimalSlotsStateController.updateState()
    }

}
