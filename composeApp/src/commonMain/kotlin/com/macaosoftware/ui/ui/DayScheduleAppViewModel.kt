package com.macaosoftware.ui.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.macaosoftware.ui.dailyagenda.decimalslots.DecimalEvent
import com.macaosoftware.ui.dailyagenda.decimalslots.DecimalSlotConfig
import com.macaosoftware.ui.dailyagenda.decimalslots.DecimalSlotsStateController
import com.macaosoftware.ui.dailyagenda.decimalslots.EventWidthType
import com.macaosoftware.ui.dailyagenda.decimalslots.EventsArrangement
import com.macaosoftware.ui.dailyagenda.timeslots.LocalTimeEvent
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

    var slotsViewType by mutableStateOf<SlotsViewType>(value = SlotsViewType.Timeline)

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

        override fun showAddEventForm(slotsViewType: SlotsViewType) {
            when (slotsViewType) {
                SlotsViewType.Decimal -> {
                    calendarEventOperationsState.value =
                        CalendarEventOperationsState.AddDecimalEventRequested
                }

                SlotsViewType.Timeline -> {
                    calendarEventOperationsState.value =
                        CalendarEventOperationsState.AddTimedEventRequested
                }

                SlotsViewType.Epg -> {
                    calendarEventOperationsState.value =
                        CalendarEventOperationsState.AddEpgEventRequested
                }
            }
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

        override fun showRemoveTimeEventForm(timeEvent: LocalTimeEvent) {
            calendarEventOperationsState.value =
                CalendarEventOperationsState.RemoveTimedEventRequested(localTimeEvent = timeEvent)
        }

        override fun confirmedRemoveTimeEvent(eventTitle: String) {
            dismissInputForm()
            timeSlotsStateController.timeSlotsDataUpdater.postUpdate {
                println("Pablo confirmRemoveTimeEvent executing")
                removeEventByTitle(eventTitle = eventTitle)
            }
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

        override fun showRemoveDecimalEventForm(decimalEvent: DecimalEvent) {
            calendarEventOperationsState.value =
                CalendarEventOperationsState.RemoveDecimalEventRequested(decimalEvent)
        }

        override fun confirmedRemoveDecimalEvent(eventTitle: String) {
            dismissInputForm()
            decimalSlotsStateController.decimalSlotsDataUpdater.postUpdate {
                removeDecimalEventByTittle(eventTitle = eventTitle)
            }
        }

        override fun toggleAxisType(slotsViewType: SlotsViewType) {
            if (this@DayScheduleAppViewModel.slotsViewType == slotsViewType) return
            this@DayScheduleAppViewModel.slotsViewType = slotsViewType
        }

        override fun dismissInputForm() {
            calendarEventOperationsState.value = CalendarEventOperationsState.Hidden
        }

        override fun onTimeEventClicked(timeEvent: LocalTimeEvent) {
            calendarEventOperationsState.value =
                CalendarEventOperationsState.ShowTimedEventRequested(localTimeEvent = timeEvent)
        }

        override fun onTimeEventDoubleClicked(timeEvent: LocalTimeEvent) {
            showRemoveTimeEventForm(timeEvent)
        }

        override fun onTimeEventLongClicked(timeEvent: LocalTimeEvent) {
            showRemoveTimeEventForm(timeEvent)
        }

        override fun onDecimalEventClicked(decimalEvent: DecimalEvent) {
            calendarEventOperationsState.value =
                CalendarEventOperationsState.ShowDecimalEventRequested(decimalEvent)
        }

        override fun onDecimalEventDoubleClicked(decimalEvent: DecimalEvent) {
            showRemoveDecimalEventForm(decimalEvent)
        }

        override fun onDecimalEventLongClicked(decimalEvent: DecimalEvent) {
            showRemoveDecimalEventForm(decimalEvent)
        }

        override fun onEpgEventClicked(localTimeEvent: LocalTimeEvent) {
            calendarEventOperationsState.value =
                CalendarEventOperationsState.ShowEpgEventRequested(epgEvent = localTimeEvent)
        }

        override fun onEpgEventDoubleClicked(localTimeEvent: LocalTimeEvent) {
            calendarEventOperationsState.value =
                CalendarEventOperationsState.RemoveEpgEventRequested(epgEvent = localTimeEvent)
        }

        override fun onEpgEventLongClicked(localTimeEvent: LocalTimeEvent) {
            calendarEventOperationsState.value =
                CalendarEventOperationsState.RemoveEpgEventRequested(epgEvent = localTimeEvent)
        }
    }

    interface UiActionListener {
        fun showAddEventForm(slotsViewType: SlotsViewType)
        fun confirmedAddTimeEvent(title: String, startLocalTime: LocalTime, endLocalTime: LocalTime)
        fun showRemoveTimeEventForm(timeEvent: LocalTimeEvent)
        fun confirmedRemoveTimeEvent(eventTitle: String)
        fun confirmedAddDecimalSegment(title: String, startValue: Float, endValue: Float)
        fun showRemoveDecimalEventForm(decimalEvent: DecimalEvent)
        fun confirmedRemoveDecimalEvent(eventTitle: String)
        fun toggleAxisType(slotsViewType: SlotsViewType)
        fun dismissInputForm()
        fun onTimeEventClicked(timeEvent: LocalTimeEvent)
        fun onTimeEventDoubleClicked(timeEvent: LocalTimeEvent)
        fun onTimeEventLongClicked(timeEvent: LocalTimeEvent)
        fun onDecimalEventClicked(decimalEvent: DecimalEvent)
        fun onDecimalEventDoubleClicked(decimalEvent: DecimalEvent)
        fun onDecimalEventLongClicked(decimalEvent: DecimalEvent)
        fun onEpgEventClicked(localTimeEvent: LocalTimeEvent)
        fun onEpgEventDoubleClicked(localTimeEvent: LocalTimeEvent)
        fun onEpgEventLongClicked(localTimeEvent: com.macaosoftware.ui.dailyagenda.timeslots.LocalTimeEvent)
    }
}
