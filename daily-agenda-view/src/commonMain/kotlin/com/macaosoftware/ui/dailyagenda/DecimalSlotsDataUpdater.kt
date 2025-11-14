package com.macaosoftware.ui.dailyagenda

open class DecimalSlotsDataUpdater(
    val dailyAgendaStateController: DailyAgendaStateController
) {

    private var isListOperation = false
    private val slotToEventMapSortedTemp: MutableMap<Slot, MutableList<Event>> = mutableMapOf()

    fun addEvent(
        startValue: Float,
        endValue: Float,
        title: String
    ): Boolean {
        val eventSlot = dailyAgendaStateController.getSlotForValue(startValue = startValue)
        val siblingEvents =
            dailyAgendaStateController.slotToEventMapSorted[eventSlot] ?: mutableListOf()

        var insertionIndex = 0
        for (idx in 0..siblingEvents.lastIndex) {
            if (siblingEvents[idx].endValue < endValue) {
                insertionIndex = idx; break
            }
        }
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
        val slot = dailyAgendaStateController.getSlotForValue(startValue = startValue)

        if (slotToEventMapSortedTemp.contains(slot)) {
            slotToEventMapSortedTemp[slot]!!.addAll(elements = segments)
        } else {
            slotToEventMapSortedTemp.put(slot, segments.toMutableList())
        }
    }

    fun removeEvent(event: Event): Boolean {
        val eventSlot = dailyAgendaStateController.getSlotForValue(startValue = event.startValue)
        val siblingEvents =
            dailyAgendaStateController.slotToEventMapSorted[eventSlot]?.toMutableList()
                ?: return false
        return siblingEvents.remove(event)
    }

    fun commit() {

        if (isListOperation) {
            val slotToEventMapSortedMerge: MutableMap<Slot, MutableList<Event>> = mutableMapOf()

            for (slot in dailyAgendaStateController.slots) {
                val addedSegments: MutableList<Event>? = slotToEventMapSortedTemp[slot]
                val existingSegments: MutableList<Event> =
                    dailyAgendaStateController.slotToEventMapSorted[slot]!!

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
                val eventsSortedByEndTime =
                    entry.value.sortedWith(endTimeComparator).toMutableList()
                dailyAgendaStateController.slotToEventMapSorted.put(
                    entry.key,
                    eventsSortedByEndTime
                )
            }
        }

        dailyAgendaStateController.updateState()
    }

}
