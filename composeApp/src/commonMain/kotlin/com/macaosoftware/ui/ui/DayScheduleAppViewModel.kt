package com.macaosoftware.ui.ui

import androidx.compose.runtime.mutableStateOf
import com.macaosoftware.ui.dailyagenda.decimalslots.DecimalEvent
import com.macaosoftware.ui.dailyagenda.decimalslots.DecimalSlotsStateController
import com.macaosoftware.ui.dailyagenda.decimalslots.EventWidthType
import com.macaosoftware.ui.dailyagenda.decimalslots.EventsArrangement
import com.macaosoftware.ui.dailyagenda.timeslots.LocalTimeEvent
import com.macaosoftware.ui.dailyagenda.decimalslots.DecimalSlotConfig
import com.macaosoftware.ui.dailyagenda.timeslots.TimeSlotConfig
import com.macaosoftware.ui.dailyagenda.timeslots.TimeSlotsStateController
import com.macaosoftware.ui.data.Constants
import com.macaosoftware.ui.data.DecimalSegmentDataSample
import com.macaosoftware.ui.data.TimeEventDataSample
import com.macaosoftware.ui.ui.model.AllDayEvent
import kotlinx.datetime.LocalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DayScheduleAppViewModel {

    val allDayEvents = mutableListOf<AllDayEvent>()

    @OptIn(ExperimentalUuidApi::class)
    fun addAllDayEvent(
        uuid: Uuid = Uuid.random(),
        title: String,
        description: String
    ): Boolean {
        return addAllDayEvent(
            AllDayEvent(
                uuid = uuid,
                title = title,
                description = description
            )
        )
    }

    fun addAllDayEvent(allDayEvent: AllDayEvent): Boolean {
        return allDayEvents.add(allDayEvent)
    }

    var showTimeSlots = mutableStateOf(true)

    val timeSlotsStateController by lazy {
        TimeSlotsStateController(
            timeSlotConfig = TimeSlotConfig(
                startSlotTime = LocalTime(7, 0), // 7:00 AM
                endSlotTime = LocalTime(19, 0), // 7:00 PM
                useAmPm = true,
                slotScale = 2,
                slotHeight = 48
            ),
            eventsArrangement = EventsArrangement.MixedDirections(EventWidthType.FixedSizeFillLastEvent)
        ).apply {
            // Prepare the initial data
            TimeEventDataSample(timeSlotsStateController = this)
        }
    }

    val decimalSlotsStateController by lazy {
        DecimalSlotsStateController(
            decimalSlotConfig = DecimalSlotConfig(
                initialSlotValue = 7.0F,
                lastSlotValue = 19.0F,
                slotScale = 2,
                slotHeight = 48
            ),
            eventsArrangement = EventsArrangement.MixedDirections(EventWidthType.FixedSizeFillLastEvent)
        ).apply {
            // Prepare the initial data
            DecimalSegmentDataSample(decimalSlotsStateController = this)
        }
    }

    var calendarEventOperationsState =
        mutableStateOf<CalendarEventOperationsState>(CalendarEventOperationsState.Hidden)

    @OptIn(ExperimentalUuidApi::class)
    val uiActionListener = object : UiActionListener {
        override fun showAddTimeEventForm() {
            calendarEventOperationsState.value = CalendarEventOperationsState.AddTimedEventRequested
        }

        override fun confirmedAddTimeEvent(
            title: String,
            startLocalTime: LocalTime,
            endLocalTime: LocalTime
        ) {
            dismissInputForm()
            timeSlotsStateController.timeSlotsDataUpdater.postUpdate {
                addEvent(
                    title = title,
                    description = Constants.EmptyDescription,
                    startTime = startLocalTime,
                    endTime = endLocalTime
                )
            }
        }

        override fun showRemoveTimeEventForm() {
            calendarEventOperationsState.value =
                CalendarEventOperationsState.RemoveTimedEventRequested.Empty
        }

        override fun showRemoveTimeEventForm(timeEvent: LocalTimeEvent) {
            calendarEventOperationsState.value =
                CalendarEventOperationsState.RemoveTimedEventRequested.WithEvent(timeEvent)
        }

        override fun confirmedRemoveTimeEvent(eventTitle: String) {
            dismissInputForm()
            timeSlotsStateController.timeSlotsDataUpdater.postUpdate {
                println("Pablo confirmRemoveTimeEvent executing")
                removeEventByTitle(eventTitle = eventTitle)
            }
        }

        override fun showAddDecimalSegmentForm() {
            calendarEventOperationsState.value =
                CalendarEventOperationsState.AddDecimalEventRequested
        }

        override fun confirmedAddDecimalSegment(
            title: String,
            startValue: Float,
            endValue: Float
        ) {
            dismissInputForm()
            decimalSlotsStateController.decimalSlotsDataUpdater.postUpdate {
                addDecimalEvent(
                    title = title,
                    description = Constants.EmptyDescription,
                    startValue = startValue,
                    endValue = endValue
                )
            }
        }

        override fun showRemoveDecimalEventForm() {
            calendarEventOperationsState.value =
                CalendarEventOperationsState.RemoveDecimalEventRequested.Empty
        }

        override fun showRemoveDecimalEventForm(decimalEvent: DecimalEvent) {
            calendarEventOperationsState.value =
                CalendarEventOperationsState.RemoveDecimalEventRequested.WithEvent(decimalEvent)
        }

        override fun confirmedRemoveDecimalEvent(eventTitle: String) {
            dismissInputForm()
            decimalSlotsStateController.decimalSlotsDataUpdater.postUpdate {
                removeDecimalEventByTittle(eventTitle = eventTitle)
            }
        }

        override fun toggleAxisType() {
            showTimeSlots.value = !showTimeSlots.value
        }

        override fun dismissInputForm() {
            calendarEventOperationsState.value = CalendarEventOperationsState.Hidden
        }

        override fun onTimeEventClicked(timeEvent: LocalTimeEvent) {
            // showRemoveTimeEventForm()
        }

        override fun onTimeEventDoubleClicked(timeEvent: LocalTimeEvent) {
            showRemoveTimeEventForm(timeEvent)
        }

        override fun onTimeEventLongClicked(timeEvent: LocalTimeEvent) {
            showRemoveTimeEventForm(timeEvent)
        }

        override fun onDecimalEventClicked(decimalEvent: DecimalEvent) {
            // showRemoveDecimalEventForm()
        }

        override fun onDecimalEventDoubleClicked(decimalEvent: DecimalEvent) {
            showRemoveDecimalEventForm(decimalEvent)
        }

        override fun onDecimalEventLongClicked(decimalEvent: DecimalEvent) {
            showRemoveDecimalEventForm(decimalEvent)
        }
    }

    interface UiActionListener {
        fun showAddTimeEventForm()
        fun confirmedAddTimeEvent(title: String, startLocalTime: LocalTime, endLocalTime: LocalTime)
        fun showRemoveTimeEventForm()
        fun showRemoveTimeEventForm(timeEvent: LocalTimeEvent)
        fun confirmedRemoveTimeEvent(eventTitle: String)
        fun showAddDecimalSegmentForm()
        fun confirmedAddDecimalSegment(title: String, startValue: Float, endValue: Float)
        fun showRemoveDecimalEventForm()
        fun showRemoveDecimalEventForm(decimalEvent: DecimalEvent)
        fun confirmedRemoveDecimalEvent(eventTitle: String)
        fun toggleAxisType()
        fun dismissInputForm()
        fun onTimeEventClicked(timeEvent: LocalTimeEvent)
        fun onTimeEventDoubleClicked(timeEvent: LocalTimeEvent)
        fun onTimeEventLongClicked(timeEvent: LocalTimeEvent)
        fun onDecimalEventClicked(decimalEvent: DecimalEvent)
        fun onDecimalEventDoubleClicked(decimalEvent: DecimalEvent)
        fun onDecimalEventLongClicked(decimalEvent: DecimalEvent)
    }
}
