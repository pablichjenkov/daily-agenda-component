package com.macaosoftware.ui.data

import com.macaosoftware.ui.dailyagenda.LocalTimeEvent
import com.macaosoftware.ui.dailyagenda.TimeSlotsStateController
import kotlinx.datetime.LocalTime

class TimeEventDataSample(timeSlotsStateController: TimeSlotsStateController) {

    init {
        timeSlotsStateController.timeSlotsDataUpdater.postUpdate {
            addEvent(
                startTime = LocalTime(hour = 8, minute = 0),
                endTime = LocalTime(hour = 8, minute = 30),
                title = "S_EV 0"
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
                startTime = LocalTime(hour = 8, minute = 0),
                endTime = LocalTime(hour = 9, minute = 0),
                title = "S_EV 1"
            )
        }
    }

    fun createLocalTimeEventsFor800AM(): List<LocalTimeEvent> {
        return listOf(
            LocalTimeEvent(
                title = "Ev 1",
                startTime = LocalTime(8, 15),
                endTime = LocalTime(11, 0)
            ),
            LocalTimeEvent(
                title = "Ev 2",
                startTime = LocalTime(8, 0),
                endTime = LocalTime(8, 45)
            ),
            LocalTimeEvent(
                title = "Ev 3",
                startTime = LocalTime(8, 6),
                endTime = LocalTime(8, 25)
            ),
        )
    }

    fun createLocalTimeEventsFor830AM(): List<LocalTimeEvent> {
        return listOf(
            LocalTimeEvent(
                title = "Evt 4",
                startTime = LocalTime(8, 40),
                endTime = LocalTime(11, 5)
            ),
            LocalTimeEvent(
                title = "Evt 5",
                startTime = LocalTime(8, 50),
                endTime = LocalTime(9, 30)
            ),
            LocalTimeEvent(
                title = "Evt 6",
                startTime = LocalTime(8, 30),
                endTime = LocalTime(9, 30)
            ),
            LocalTimeEvent(
                title = "Evt 7",
                startTime = LocalTime(8, 30),
                endTime = LocalTime(9, 0)
            )
        )
    }

    fun createLocalTimeEventsFor900AM(): List<LocalTimeEvent> {
        return listOf(
            LocalTimeEvent(
                title = "Evt 8",
                startTime = LocalTime(9, 0),
                endTime = LocalTime(9, 30)
            ),
            LocalTimeEvent(
                title = "Evt 9",
                startTime = LocalTime(9, 12),
                endTime = LocalTime(10, 0)
            ),
            LocalTimeEvent(
                title = "Evt 10",
                startTime = LocalTime(9, 15),
                endTime = LocalTime(10, 0)
            )
        )
    }

    fun createLocalTimeEventsFor930AM(): List<LocalTimeEvent> {
        return listOf(
            LocalTimeEvent(
                title = "Evt 11",
                startTime = LocalTime(9, 30),
                endTime = LocalTime(11, 0)
            ),
            LocalTimeEvent(
                title = "Evt 12",
                startTime = LocalTime(9, 30),
                endTime = LocalTime(10, 30)
            ),
            LocalTimeEvent(
                title = "Evt 13",
                startTime = LocalTime(9, 50),
                endTime = LocalTime(10, 25)
            ),
            LocalTimeEvent(
                title = "Evt 14",
                startTime = LocalTime(9, 40),
                endTime = LocalTime(10, 0)
            ),
            LocalTimeEvent(
                title = "Evt 15",
                startTime = LocalTime(9, 55),
                endTime = LocalTime(10, 12)
            )
        )
    }

}
