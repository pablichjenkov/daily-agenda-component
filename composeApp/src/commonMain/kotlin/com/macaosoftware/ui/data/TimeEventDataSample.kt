package com.macaosoftware.ui.data

import com.macaosoftware.ui.dailyagenda.timeslots.LocalTimeEvent
import com.macaosoftware.ui.dailyagenda.timeslots.TimeSlotsStateController
import kotlinx.datetime.LocalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class TimeEventDataSample(timeSlotsStateController: TimeSlotsStateController) {

    init {
        timeSlotsStateController.timeSlotsDataUpdater.postUpdate {
            addEvent(
                uuid = Uuid.random(),
                title = "EV0",
                description = Constants.EmptyDescription,
                startTime = LocalTime(hour = 8, minute = 0),
                endTime = LocalTime(hour = 8, minute = 30),
            )
            addEventList(
                startTime = LocalTime(hour = 8, minute = 0),
                events = createLocalTimeEventsFor800AM()
            )
            addEventList(
                startTime = LocalTime(hour = 8, minute = 30),
                events = createLocalTimeEventsFor830AM()
            )
            addEventList(
                startTime = LocalTime(hour = 9, minute = 0),
                events = createLocalTimeEventsFor900AM()
            )
            addEventList(
                startTime = LocalTime(hour = 9, minute = 30),
                events = createLocalTimeEventsFor930AM()
            )
            addEvent(
                uuid = Uuid.random(),
                title = "EVL",
                description = Constants.EmptyDescription,
                startTime = LocalTime(hour = 8, minute = 0),
                endTime = LocalTime(hour = 9, minute = 0),
            )
        }
    }

    fun createLocalTimeEventsFor800AM(): List<LocalTimeEvent> {
        return listOf(
            LocalTimeEvent(
                uuid = Uuid.random(),
                title = "Ev1",
                description = Constants.EmptyDescription,
                startTime = LocalTime(8, 15),
                endTime = LocalTime(11, 0)
            ),
            LocalTimeEvent(
                uuid = Uuid.random(),
                title = "Ev2",
                description = Constants.EmptyDescription,
                startTime = LocalTime(8, 0),
                endTime = LocalTime(8, 45)
            ),
            LocalTimeEvent(
                uuid = Uuid.random(),
                title = "Ev3",
                description = Constants.EmptyDescription,
                startTime = LocalTime(8, 6),
                endTime = LocalTime(8, 25)
            ),
        )
    }

    fun createLocalTimeEventsFor830AM(): List<LocalTimeEvent> {
        return listOf(
            LocalTimeEvent(
                uuid = Uuid.random(),
                title = "Ev4",
                description = Constants.EmptyDescription,
                startTime = LocalTime(8, 40),
                endTime = LocalTime(11, 5)
            ),
            LocalTimeEvent(
                uuid = Uuid.random(),
                title = "Ev5",
                description = Constants.EmptyDescription,
                startTime = LocalTime(8, 50),
                endTime = LocalTime(9, 30)
            ),
            LocalTimeEvent(
                uuid = Uuid.random(),
                title = "Ev6",
                description = Constants.EmptyDescription,
                startTime = LocalTime(8, 30),
                endTime = LocalTime(9, 30)
            ),
            LocalTimeEvent(
                uuid = Uuid.random(),
                title = "Ev7",
                description = Constants.EmptyDescription,
                startTime = LocalTime(8, 30),
                endTime = LocalTime(9, 0)
            )
        )
    }

    fun createLocalTimeEventsFor900AM(): List<LocalTimeEvent> {
        return listOf(
            LocalTimeEvent(
                uuid = Uuid.random(),
                title = "Ev8",
                description = Constants.EmptyDescription,
                startTime = LocalTime(9, 0),
                endTime = LocalTime(9, 30)
            ),
            LocalTimeEvent(
                uuid = Uuid.random(),
                title = "Ev9",
                description = Constants.EmptyDescription,
                startTime = LocalTime(9, 12),
                endTime = LocalTime(10, 0)
            ),
            LocalTimeEvent(
                uuid = Uuid.random(),
                title = "Ev10",
                description = Constants.EmptyDescription,
                startTime = LocalTime(9, 15),
                endTime = LocalTime(10, 0)
            )
        )
    }

    fun createLocalTimeEventsFor930AM(): List<LocalTimeEvent> {
        return listOf(
            LocalTimeEvent(
                uuid = Uuid.random(),
                title = "Ev11",
                description = Constants.EmptyDescription,
                startTime = LocalTime(9, 30),
                endTime = LocalTime(11, 0)
            ),
            LocalTimeEvent(
                uuid = Uuid.random(),
                title = "Ev12",
                description = Constants.EmptyDescription,
                startTime = LocalTime(9, 30),
                endTime = LocalTime(10, 30)
            ),
            LocalTimeEvent(
                uuid = Uuid.random(),
                title = "Ev13",
                description = Constants.EmptyDescription,
                startTime = LocalTime(9, 50),
                endTime = LocalTime(10, 25)
            ),
            LocalTimeEvent(
                uuid = Uuid.random(),
                title = "Ev14",
                description = Constants.EmptyDescription,
                startTime = LocalTime(9, 40),
                endTime = LocalTime(10, 0)
            ),
            LocalTimeEvent(
                uuid = Uuid.random(),
                title = "Ev15",
                description = Constants.EmptyDescription,
                startTime = LocalTime(9, 55),
                endTime = LocalTime(10, 12)
            )
        )
    }

}
