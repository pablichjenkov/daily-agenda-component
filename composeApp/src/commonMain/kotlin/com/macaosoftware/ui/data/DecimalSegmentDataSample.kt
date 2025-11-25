package com.macaosoftware.ui.data

import com.macaosoftware.ui.dailyagenda.decimalslots.DecimalSlotsStateController
import com.macaosoftware.ui.dailyagenda.decimalslots.DecimalEvent
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DecimalSegmentDataSample(decimalSlotsStateController: DecimalSlotsStateController) {

    init {
        decimalSlotsStateController.decimalSlotsDataUpdater.postUpdate {
            addDecimalEventList(
                startValue = 8.0F,
                segments = createSegmentsFor8()
            )
            addDecimalEventList(
                startValue = 8.5F,
                segments = createEventsFor8_5()
            )
            addDecimalEventList(
                startValue = 9.0F,
                segments = createEventsFor9()
            )
            addDecimalEventList(
                startValue = 9.5F,
                segments = createEventsFor9_5()
            )
            addDecimalEventList(
                startValue = 10.0F,
                segments = createEventsFor10()
            )
            addDecimalEventList(
                startValue = 10.5F,
                segments = createEventsFor10_5()
            )
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun createSegmentsFor8(): List<DecimalEvent> {
        return listOf(
            DecimalEvent(
                uuid = Uuid.random(),
                title = "Ev1",
                description = Constants.EmptyDescription,
                startValue = 8.0F,
                endValue = 10.0F
            ),
            DecimalEvent(
                uuid = Uuid.random(),
                title = "Ev2",
                description = Constants.EmptyDescription,
                startValue = 8.0F,
                endValue = 9.5F
            ),
            DecimalEvent(
                uuid = Uuid.random(),
                title = "Ev3",
                description = Constants.EmptyDescription,
                startValue = 8.0F,
                endValue = 9.0F
            ),
            DecimalEvent(
                uuid = Uuid.random(),
                title = "Ev4",
                description = Constants.EmptyDescription,
                startValue = 8.0F,
                endValue = 8.75F
            ),
            DecimalEvent(
                uuid = Uuid.random(),
                title = "Ev5",
                description = Constants.EmptyDescription,
                startValue = 8.0F,
                endValue = 8.25F
            ),
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun createEventsFor8_5(): List<DecimalEvent> {
        return listOf(
            DecimalEvent(
                uuid = Uuid.random(),
                title = "Ev6",
                description = Constants.EmptyDescription,
                startValue = 8.5F,
                endValue = 11.0F
            ),
            DecimalEvent(
                uuid = Uuid.random(),
                title = "Ev7",
                description = Constants.EmptyDescription,
                startValue = 8.5F,
                endValue = 9.5F
            ),
            DecimalEvent(
                uuid = Uuid.random(),
                title = "Ev8",
                description = Constants.EmptyDescription,
                startValue = 8.5F,
                endValue = 9.0F
            ),
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun createEventsFor9(): List<DecimalEvent> {
        return listOf(
            DecimalEvent(
                uuid = Uuid.random(),
                title = "Ev9",
                description = Constants.EmptyDescription,
                startValue = 9.0F,
                endValue = 10.0F
            ),
            DecimalEvent(
                uuid = Uuid.random(),
                title = "Ev10",
                description = Constants.EmptyDescription,
                startValue = 9.0F,
                endValue = 10.0F
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun createEventsFor9_5(): List<DecimalEvent> {
        return listOf(
            DecimalEvent(
                uuid = Uuid.random(),
                title = "Ev11",
                description = Constants.EmptyDescription,
                startValue = 9.5F,
                endValue = 11.0F
            ),
            DecimalEvent(
                uuid = Uuid.random(),
                title = "Ev12",
                description = Constants.EmptyDescription,
                startValue = 9.5F,
                endValue = 10.5F
            ),
            DecimalEvent(
                uuid = Uuid.random(),
                title = "Ev13",
                description = Constants.EmptyDescription,
                startValue = 9.5F,
                endValue = 10.0F
            ),
            DecimalEvent(
                uuid = Uuid.random(),
                title = "Ev14",
                description = Constants.EmptyDescription,
                startValue = 9.5F,
                endValue = 10.0F
            ),
            DecimalEvent(
                uuid = Uuid.random(),
                title = "Ev15",
                description = Constants.EmptyDescription,
                startValue = 9.5F,
                endValue = 10.0F
            ),
            DecimalEvent(
                uuid = Uuid.random(),
                title = "Ev16",
                description = Constants.EmptyDescription,
                startValue = 9.5F,
                endValue = 10.0F
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun createEventsFor10(): List<DecimalEvent> {
        return listOf(
            DecimalEvent(
                uuid = Uuid.random(),
                title = "Ev17",
                description = Constants.EmptyDescription,
                startValue = 10.0F,
                endValue = 11.5F
            ),
            DecimalEvent(
                uuid = Uuid.random(),
                title = "Ev18",
                description = Constants.EmptyDescription,
                startValue = 10.0F,
                endValue = 11.0F
            ),
            DecimalEvent(
                uuid = Uuid.random(),
                title = "Ev19",
                description = Constants.EmptyDescription,
                startValue = 10.0F,
                endValue = 10.5F
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun createEventsFor10_5(): List<DecimalEvent> {
        return listOf(
            DecimalEvent(
                uuid = Uuid.random(),
                title = "Ev20",
                description = Constants.EmptyDescription,
                startValue = 10.5F,
                endValue = 11.5F
            ),
            DecimalEvent(
                uuid = Uuid.random(),
                title = "Ev21",
                description = Constants.EmptyDescription,
                startValue = 10.5F,
                endValue = 11.0F
            )
        )
    }

}
