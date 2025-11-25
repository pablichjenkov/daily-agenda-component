package com.macaosoftware.ui.dailyagenda.timeslots

import com.macaosoftware.ui.dailyagenda.decimalslots.DecimalSlotsBaseLayoutStateController
import com.macaosoftware.ui.dailyagenda.decimalslots.DecimalSlotsDataUpdater
import kotlinx.datetime.LocalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class TimeSlotsDataUpdater internal constructor(
    decimalSlotsBaseLayoutStateController: DecimalSlotsBaseLayoutStateController
) {

    private val decimalSlotsDataUpdater =
        DecimalSlotsDataUpdater(decimalSlotsBaseLayoutStateController)

    @OptIn(ExperimentalUuidApi::class)
    fun addEvent(
        uuid: Uuid = Uuid.random(),
        title: String,
        description: String,
        startTime: LocalTime,
        endTime: LocalTime
    ): Boolean {
        return decimalSlotsDataUpdater.addDecimalEvent(
            uuid = uuid,
            title = title,
            description = description,
            startValue = fromLocalTimeToValue(localTime = startTime),
            endValue = fromLocalTimeToValue(localTime = endTime)
        )
    }

    fun addEvent(event: LocalTimeEvent): Boolean {
        return decimalSlotsDataUpdater.addDecimalEvent(decimalEvent = event.toDecimalSegment())
    }

    fun addEventList(startTime: LocalTime, events: List<LocalTimeEvent>) {
        return decimalSlotsDataUpdater.addDecimalEventList(
            startValue = fromLocalTimeToValue(localTime = startTime),
            segments = events.map { it.toDecimalSegment() }
        )
    }

    fun removeEvent(event: LocalTimeEvent): Boolean {
        return decimalSlotsDataUpdater.removeDecimalEvent(decimalEvent = event.toDecimalSegment())
    }

    fun removeEventByTitle(eventTitle: String): Boolean {
        return decimalSlotsDataUpdater.removeDecimalEventByTittle(eventTitle = eventTitle)
    }

    fun postUpdate(block: TimeSlotsDataUpdater.() -> Unit) {
        this.block()
        decimalSlotsDataUpdater.commit()
    }

}
