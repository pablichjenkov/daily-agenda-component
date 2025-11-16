package com.macaosoftware.ui

import androidx.compose.runtime.mutableStateOf
import com.macaosoftware.ui.dailyagenda.DecimalSlotsStateController
import com.macaosoftware.ui.dailyagenda.EventWidthType
import com.macaosoftware.ui.dailyagenda.EventsArrangement
import com.macaosoftware.ui.dailyagenda.SlotConfig
import com.macaosoftware.ui.dailyagenda.TimeSlotConfig
import com.macaosoftware.ui.dailyagenda.TimeSlotsStateController
import com.macaosoftware.ui.data.Sample0
import com.macaosoftware.ui.data.Sample1
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
        Sample0(timeSlotsStateController = this)
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
            Sample1(decimalSlotsStateController = this)
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
            // calendarEventOperationsState.value = CalendarEventOperationsState.Hidden
            timeSlotsStateController.timeSlotsDataUpdater.run {
                addEvent(
                    title = title,
                    startTime = startLocalTime,
                    endTime = endLocalTime
                )
                commit()
            }
        }

        override fun showRemoveTimeEventForm() {
            calendarEventOperationsState.value = CalendarEventOperationsState.RemoveTimedEventRequested
        }

        override fun removeTimeEvent() {

        }

        override fun showAddDecimalSegmentForm() {
            calendarEventOperationsState.value = CalendarEventOperationsState.AddDecimalSegmentRequested
        }

        override fun confirmAddDecimalSegment(
            title: String,
            startValue: Float,
            endValue: Float
        ) {
            // calendarEventOperationsState.value = CalendarEventOperationsState.Hidden
            decimalSlotsStateController.decimalSlotsDataUpdater.run {
                addEvent(
                    title = title,
                    startValue = startValue,
                    endValue = endValue
                )
                commit()
            }
        }

        override fun showRemoveSegmentForm() {
            calendarEventOperationsState.value = CalendarEventOperationsState.RemoveDecimalSegmentRequested
        }

        override fun removeDecimalSegment() {

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
        fun removeTimeEvent()
        fun showAddDecimalSegmentForm()
        fun confirmAddDecimalSegment(title: String, startValue: Float, endValue: Float)
        fun showRemoveSegmentForm()
        fun removeDecimalSegment()
        fun toggleAxisType()
        fun dismissInputForm()
    }
}