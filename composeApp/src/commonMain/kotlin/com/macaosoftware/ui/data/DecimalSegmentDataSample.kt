package com.macaosoftware.ui.data

import com.macaosoftware.ui.dailyagenda.DecimalSlotsStateController
import com.macaosoftware.ui.dailyagenda.Event

class DecimalSegmentDataSample(decimalSlotsStateController: DecimalSlotsStateController) {

    init {
        decimalSlotsStateController.decimalSlotsDataUpdater.postUpdate {
            addDecimalSegmentList(
                startValue = 8.0F,
                segments = createSegmentsFor8()
            )
            addDecimalSegmentList(
                startValue = 8.5F,
                segments = createEventsFor8_5()
            )
            addDecimalSegmentList(
                startValue = 9.0F,
                segments = createEventsFor9()
            )
            addDecimalSegmentList(
                startValue = 9.5F,
                segments = createEventsFor9_5()
            )
            addDecimalSegmentList(
                startValue = 10.0F,
                segments = createEventsFor10()
            )
            addDecimalSegmentList(
                startValue = 10.5F,
                segments = createEventsFor10_5()
            )
        }
    }

    private fun createSegmentsFor8(): List<Event> {
        return listOf(
            Event(
                title = "Ev 1",
                startValue = 8.0F,
                endValue = 10.0F
            ),
            Event(
                title = "Ev 2",
                startValue = 8.0F,
                endValue = 9.5F
            ),
            Event(
                title = "Ev 3",
                startValue = 8.0F,
                endValue = 9.0F
            ),
            Event(
                title = "Ev 4",
                startValue = 8.0F,
                endValue = 8.75F
            ),
            Event(
                title = "Ev 5",
                startValue = 8.0F,
                endValue = 8.25F
            ),
        )
    }

    private fun createEventsFor8_5(): List<Event> {
        return listOf(
            Event(
                title = "Evt 6",
                startValue = 8.5F,
                endValue = 11.0F
            ),
            Event(
                title = "Evt 7",
                startValue = 8.5F,
                endValue = 9.5F
            ),
            Event(
                title = "Evt 8",
                startValue = 8.5F,
                endValue = 9.0F
            ),
        )
    }

    private fun createEventsFor9(): List<Event> {
        return listOf(
            Event(
                title = "Evt 9",
                startValue = 9.0F,
                endValue = 10.0F
            ),
            Event(
                title = "Evt 10",
                startValue = 9.0F,
                endValue = 10.0F
            )
        )
    }

    private fun createEventsFor9_5(): List<Event> {
        return listOf(
            Event(
                title = "Evt 11",
                startValue = 9.5F,
                endValue = 11.0F
            ),
            Event(
                title = "Evt 12",
                startValue = 9.5F,
                endValue = 10.5F
            ),
            Event(
                title = "Evt 13",
                startValue = 9.5F,
                endValue = 10.0F
            ),
            Event(
                title = "Evt 14",
                startValue = 9.5F,
                endValue = 10.0F
            ),
            Event(
                title = "Evt 15",
                startValue = 9.5F,
                endValue = 10.0F
            ),
            Event(
                title = "Evt 16",
                startValue = 9.5F,
                endValue = 10.0F
            )
        )
    }

    private fun createEventsFor10(): List<Event> {
        return listOf(
            Event(
                title = "Evt 17",
                startValue = 10.0F,
                endValue = 11.5F
            ),
            Event(
                title = "Evt 18",
                startValue = 10.0F,
                endValue = 11.0F
            ),
            Event(
                title = "Evt 19",
                startValue = 10.0F,
                endValue = 10.5F
            )
        )
    }

    private fun createEventsFor10_5(): List<Event> {
        return listOf(
            Event(
                title = "Evt 20",
                startValue = 10.5F,
                endValue = 11.5F
            ),
            Event(
                title = "Evt 21",
                startValue = 10.5F,
                endValue = 11.0F
            )
        )
    }

}
