package com.macaosoftware.ui.dailyagenda

import kotlinx.datetime.LocalTime

class TimeSlotsDataUpdater internal constructor(
    dailyAgendaStateController: DailyAgendaStateController
) {

    private val decimalSlotsDataUpdater = DecimalSlotsDataUpdater(dailyAgendaStateController)

    fun addEvent(
        startTime: LocalTime,
        endTime: LocalTime,
        title: String
    ): Boolean {
        return decimalSlotsDataUpdater.addDecimalSegment(
            startValue = fromLocalTimeToValue(localTime = startTime),
            endValue = fromLocalTimeToValue(localTime = endTime),
            title = title
        )
    }

    fun addEvent(event: LocalTimeEvent): Boolean {
        return decimalSlotsDataUpdater.addDecimalSegment(event = event.toEvent())
    }

    fun addEventList(startTime: LocalTime, events: List<LocalTimeEvent>) {
        return decimalSlotsDataUpdater.addDecimalSegmentList(
            startValue = fromLocalTimeToValue(localTime = startTime),
            segments = events.map { it.toEvent() }
        )
    }

    fun removeEvent(event: LocalTimeEvent): Boolean {
        return decimalSlotsDataUpdater.removeDecimalSegment(event = event.toEvent())
    }

    fun removeEventByTitle(eventTitle: String): Boolean {
        return decimalSlotsDataUpdater.removeDecimalSegmentByTittle(eventTitle = eventTitle)
    }

    fun postUpdate(block: TimeSlotsDataUpdater.() -> Unit) {
        this.block()
        decimalSlotsDataUpdater.commit()
    }

}
