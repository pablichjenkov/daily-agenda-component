package com.macaosoftware.ui

import androidx.compose.runtime.mutableStateOf
import com.macaosoftware.ui.dailyagenda.DecimalEvent
import com.macaosoftware.ui.dailyagenda.DecimalSlotsStateController
import com.macaosoftware.ui.dailyagenda.EventWidthType
import com.macaosoftware.ui.dailyagenda.EventsArrangement
import com.macaosoftware.ui.dailyagenda.LocalTimeEvent
import com.macaosoftware.ui.dailyagenda.SlotConfig
import com.macaosoftware.ui.dailyagenda.TimeSlotConfig
import com.macaosoftware.ui.dailyagenda.TimeSlotsStateController
import com.macaosoftware.ui.data.Constants
import com.macaosoftware.ui.data.DecimalSegmentDataSample
import com.macaosoftware.ui.data.TimeEventDataSample
import kotlinx.datetime.LocalTime
import kotlin.uuid.ExperimentalUuidApi

class DayScheduleAppViewModel {

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
            slotConfig = SlotConfig(
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
