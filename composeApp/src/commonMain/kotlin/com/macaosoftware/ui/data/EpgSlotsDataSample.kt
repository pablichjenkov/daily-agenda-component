package com.macaosoftware.ui.data

import com.macaosoftware.ui.dailyagenda.epgslots.EpgChannel
import com.macaosoftware.ui.dailyagenda.epgslots.EpgSlotsStateController
import com.macaosoftware.ui.dailyagenda.timeslots.LocalTimeEvent
import kotlinx.datetime.LocalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class EpgSlotsDataSample(epgSlotsStateController: EpgSlotsStateController) {

    init {
        epgSlotsStateController.epgSlotsDataUpdater.postUpdate {
            addChannel(
                EpgChannel(
                    name = "Ch1",
                    events = listOf(
                        LocalTimeEvent(
                            uuid = Uuid.random(),
                            title = "Ev1",
                            description = Constants.EmptyDescription,
                            startTime = LocalTime(9, 0),
                            endTime = LocalTime(10, 0)
                        ),
                        LocalTimeEvent(
                            uuid = Uuid.random(),
                            title = "Ev2",
                            description = Constants.EmptyDescription,
                            startTime = LocalTime(10, 0),
                            endTime = LocalTime(11, 30)
                        )
                    )
                )
            )

            addChannel(
                EpgChannel(
                    name = "Ch2",
                    events = listOf(
                        LocalTimeEvent(
                            uuid = Uuid.random(),
                            title = "Ev3",
                            description = Constants.EmptyDescription,
                            startTime = LocalTime(9, 30),
                            endTime = LocalTime(10, 15)
                        ),
                        LocalTimeEvent(
                            uuid = Uuid.random(),
                            title = "Ev4",
                            description = Constants.EmptyDescription,
                            startTime = LocalTime(10, 30),
                            endTime = LocalTime(11, 0)
                        )
                    )
                )
            )
            addChannel(
                EpgChannel(
                    name = "Ch3",
                    events = listOf(
                        LocalTimeEvent(
                            uuid = Uuid.random(),
                            title = "Ev5",
                            description = Constants.EmptyDescription,
                            startTime = LocalTime(10, 0),
                            endTime = LocalTime(11, 0)
                        ),
                        LocalTimeEvent(
                            uuid = Uuid.random(),
                            title = "Ev6",
                            description = Constants.EmptyDescription,
                            startTime = LocalTime(12, 0),
                            endTime = LocalTime(13, 30)
                        )
                    )
                )
            )
        }
    }
}
