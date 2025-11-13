package com.macaosoftware.ui.dailyagenda

import kotlinx.datetime.LocalTime

class TimeSlotsDataUpdater(
    timeSlotsStateController: TimeSlotsStateController
) {

    private val decimalSlotsDataUpdater =
        DecimalSlotsDataUpdater(decimalSlotsStateController = timeSlotsStateController)

    fun addEvent(
        startTime: LocalTime,
        endTime: LocalTime,
        title: String
    ): Boolean {
        return decimalSlotsDataUpdater.addEvent(
            startValue = fromLocalTimeToValue(localTime = startTime),
            endValue = fromLocalTimeToValue(localTime = endTime),
            title = title
        )
    }

    fun addEvent(event: LocalTimeEvent): Boolean {
        return decimalSlotsDataUpdater.addEvent(event = event.toEvent())
    }

    fun addEventList(startTime: LocalTime, events: List<LocalTimeEvent>) {
        return decimalSlotsDataUpdater.addDecimalSegmentList(
            startValue = fromLocalTimeToValue(localTime = startTime),
            segments = events.map { it.toEvent() }
        )
    }

    fun removeEvent(event: LocalTimeEvent): Boolean {
        return decimalSlotsDataUpdater.removeEvent(event = event.toEvent())
    }

    fun commit() {
        decimalSlotsDataUpdater.commit()
    }

}
