package com.macaosoftware.ui.dailyagenda

open class DecimalSlotsDataUpdater internal constructor(
    val dailyAgendaStateController: DailyAgendaStateController
) {

    private var isListOperation = false
    private val slotToEventMapSortedTemp: MutableMap<Slot, MutableList<Event>> = mutableMapOf()

    fun addDecimalSegment(
        startValue: Float,
        endValue: Float,
        title: String
    ): Boolean {
        val eventSlot = dailyAgendaStateController.getSlotForValue(startValue = startValue)
        val siblingEvents =
            dailyAgendaStateController.slotToEventMapSorted[eventSlot] ?: mutableListOf()

        var insertionIndex = 0
        for (idx in siblingEvents.lastIndex downTo 0) {
            if (siblingEvents[idx].endValue >= endValue) {
                insertionIndex = idx + 1; break
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

    fun addDecimalSegment(event: Event): Boolean {
        return addDecimalSegment(
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

    fun removeDecimalSegmentByTittle(eventTitle: String): Boolean {
        var eventMatching: Event? = null
        val entryContainingEvent =
            dailyAgendaStateController.slotToEventMapSorted.entries.find { entry ->
                eventMatching = entry.value.find { event ->
                    event.title == eventTitle
                }
                eventMatching != null
            }

        return eventMatching?.let {
            entryContainingEvent?.value?.remove(eventMatching)
        } ?: false
    }

    fun removeDecimalSegment(event: Event): Boolean {
        val eventSlot = dailyAgendaStateController.getSlotForValue(startValue = event.startValue)
        val siblingEvents =
            dailyAgendaStateController.slotToEventMapSorted[eventSlot]?.toMutableList()
                ?: return false
        return siblingEvents.remove(event)
    }

    internal fun commit() {

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
            isListOperation = false
        }

        dailyAgendaStateController.updateState()
    }

    fun postUpdate(block: DecimalSlotsDataUpdater.() -> Unit) {
        this.block()
        commit()
    }

}
