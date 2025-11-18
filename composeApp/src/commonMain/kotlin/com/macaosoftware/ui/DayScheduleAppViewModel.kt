package com.macaosoftware.ui

import androidx.compose.runtime.mutableStateOf
import com.macaosoftware.ui.dailyagenda.DecimalSlotsStateController
import com.macaosoftware.ui.dailyagenda.EventWidthType
import com.macaosoftware.ui.dailyagenda.EventsArrangement
import com.macaosoftware.ui.dailyagenda.SlotConfig
import com.macaosoftware.ui.dailyagenda.TimeSlotConfig
import com.macaosoftware.ui.dailyagenda.TimeSlotsStateController
import com.macaosoftware.ui.data.TimeEventDataSample
import com.macaosoftware.ui.data.DecimalSegmentDataSample
import kotlinx.datetime.LocalTime

class DayScheduleAppViewModel {

    var showTimeSlots = mutableStateOf(true)

    val timeSlotsStateController = TimeSlotsStateController(
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

    val decimalSlotsStateController =
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

    var calendarEventOperationsState = mutableStateOf<CalendarEventOperationsState>(CalendarEventOperationsState.Hidden)

    val uiActionListener = object : UiActionListener {
        override fun showAddTimeEventForm() {
            calendarEventOperationsState.value = CalendarEventOperationsState.AddTimedEventRequested
        }

        override fun confirmAddTimeEvent(
            title: String,
            startLocalTime: LocalTime,
            endLocalTime: LocalTime
        ) {
            dismissInputForm()
            timeSlotsStateController.timeSlotsDataUpdater.postUpdate {
                addEvent(
                    title = title,
                    startTime = startLocalTime,
                    endTime = endLocalTime
                )
            }
        }

        override fun showRemoveTimeEventForm() {
            calendarEventOperationsState.value = CalendarEventOperationsState.RemoveTimedEventRequested
        }

        override fun confirmRemoveTimeEvent(eventTitle: String) {
            dismissInputForm()
            timeSlotsStateController.timeSlotsDataUpdater.postUpdate {
                removeEventByTitle(eventTitle = eventTitle)
            }
        }

        override fun showAddDecimalSegmentForm() {
            calendarEventOperationsState.value = CalendarEventOperationsState.AddDecimalSegmentRequested
        }

        override fun confirmAddDecimalSegment(
            title: String,
            startValue: Float,
            endValue: Float
        ) {
            dismissInputForm()
            decimalSlotsStateController.decimalSlotsDataUpdater.postUpdate {
                addDecimalSegment(
                    title = title,
                    startValue = startValue,
                    endValue = endValue
                )
            }
        }

        override fun showRemoveSegmentForm() {
            calendarEventOperationsState.value = CalendarEventOperationsState.RemoveDecimalSegmentRequested
        }

        override fun confirmRemoveDecimalSegment(eventTitle: String) {
            dismissInputForm()
            decimalSlotsStateController.decimalSlotsDataUpdater.postUpdate {
                removeDecimalSegmentByTittle(eventTitle = eventTitle)
            }
        }

        override fun toggleAxisType() {
            showTimeSlots.value = !showTimeSlots.value
        }

        override fun dismissInputForm() {
            calendarEventOperationsState.value = CalendarEventOperationsState.Hidden
        }
    }

    interface UiActionListener {
        fun showAddTimeEventForm()
        fun confirmAddTimeEvent(title: String, startLocalTime: LocalTime, endLocalTime: LocalTime)
        fun showRemoveTimeEventForm()
        fun confirmRemoveTimeEvent(eventTitle: String)
        fun showAddDecimalSegmentForm()
        fun confirmAddDecimalSegment(title: String, startValue: Float, endValue: Float)
        fun showRemoveSegmentForm()
        fun confirmRemoveDecimalSegment(eventTitle: String)
        fun toggleAxisType()
        fun dismissInputForm()
    }
}
