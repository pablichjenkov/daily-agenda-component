package com.macaosoftware.ui.dailyagenda

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

open class DecimalSlotsDataUpdater internal constructor(
    val dailyAgendaStateController: DailyAgendaStateController
) {

    private var isListOperation = false
    private val slotToDecimalEventMapSortedTemp: MutableMap<Slot, MutableList<DecimalEvent>> =
        mutableMapOf()

    @OptIn(ExperimentalUuidApi::class)
    fun addDecimalSegment(
        uuid: Uuid = Uuid.random(),
        title: String,
        description: String,
        startValue: Float,
        endValue: Float,
    ): Boolean = addDecimalSegment(
        DecimalEvent(
            uuid = uuid,
            startValue = startValue,
            endValue = endValue,
            title = title,
            description = description
        )
    )

    @OptIn(ExperimentalUuidApi::class)
    fun addDecimalSegment(decimalEvent: DecimalEvent): Boolean {
        val eventSlot =
            dailyAgendaStateController.getSlotForValue(startValue = decimalEvent.startValue)
        val siblingEvents =
            dailyAgendaStateController.slotToDecimalEventMapSorted[eventSlot] ?: mutableListOf()

        var insertionIndex = 0
        for (idx in siblingEvents.lastIndex downTo 0) {
            if (siblingEvents[idx].endValue >= decimalEvent.endValue) {
                insertionIndex = idx + 1; break
            }
        }
        siblingEvents.add(insertionIndex, decimalEvent)
        return true
    }

    fun addDecimalSegmentList(startValue: Float, segments: List<DecimalEvent>) {
        isListOperation = true
        val slot = dailyAgendaStateController.getSlotForValue(startValue = startValue)

        if (slotToDecimalEventMapSortedTemp.contains(slot)) {
            slotToDecimalEventMapSortedTemp[slot]!!.addAll(elements = segments)
        } else {
            slotToDecimalEventMapSortedTemp.put(slot, segments.toMutableList())
        }
    }

    fun removeDecimalSegmentByTittle(eventTitle: String): Boolean {
        var decimalEventMatching: DecimalEvent? = null
        val entryContainingEvent =
            dailyAgendaStateController.slotToDecimalEventMapSorted.entries.find { entry ->
                decimalEventMatching = entry.value.find { event ->
                    event.title == eventTitle
                }
                decimalEventMatching != null
            }

        return decimalEventMatching?.let {
            entryContainingEvent?.value?.remove(decimalEventMatching)
        } ?: false
    }

    fun removeDecimalSegment(decimalEvent: DecimalEvent): Boolean {
        val eventSlot =
            dailyAgendaStateController.getSlotForValue(startValue = decimalEvent.startValue)
        val siblingEvents =
            dailyAgendaStateController.slotToDecimalEventMapSorted[eventSlot]?.toMutableList()
                ?: return false
        return siblingEvents.remove(decimalEvent)
    }

    internal fun commit() {

        if (isListOperation) {
            val slotToDecimalEventMapSortedMerge: MutableMap<Slot, MutableList<DecimalEvent>> =
                mutableMapOf()

            for (slot in dailyAgendaStateController.slots) {
                val addedSegments: MutableList<DecimalEvent>? =
                    slotToDecimalEventMapSortedTemp[slot]
                val existingSegments: MutableList<DecimalEvent> =
                    dailyAgendaStateController.slotToDecimalEventMapSorted[slot]!!

                if (addedSegments != null) {
                    addedSegments.addAll(existingSegments)
                    slotToDecimalEventMapSortedMerge.put(slot, addedSegments)
                } else {
                    slotToDecimalEventMapSortedMerge.put(slot, existingSegments)
                }
            }

            /**
             * Sort the events to maximize spacing when the layout runs.
             * */
            val endTimeComparator =
                Comparator { decimalEvent1: DecimalEvent, decimalEvent2: DecimalEvent ->
                    val diff = decimalEvent2.endValue - decimalEvent1.endValue
                    when {
                        (diff > 0F) -> 1
                        (diff < 0F) -> -1
                        else -> 0
                    }
                }
            slotToDecimalEventMapSortedMerge.entries.forEach { entry ->
                val eventsSortedByEndTime =
                    entry.value.sortedWith(endTimeComparator).toMutableList()
                dailyAgendaStateController.slotToDecimalEventMapSorted.put(
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
