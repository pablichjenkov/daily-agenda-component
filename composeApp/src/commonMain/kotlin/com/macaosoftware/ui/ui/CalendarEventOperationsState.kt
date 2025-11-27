package com.macaosoftware.ui.ui

import com.macaosoftware.ui.dailyagenda.decimalslots.DecimalEvent
import com.macaosoftware.ui.dailyagenda.timeslots.LocalTimeEvent

sealed interface CalendarEventOperationsState {
    object Hidden : CalendarEventOperationsState

    class ShowTimedEventRequested(val localTimeEvent: LocalTimeEvent) :
        CalendarEventOperationsState

    object AddTimedEventRequested : CalendarEventOperationsState

    class RemoveTimedEventRequested(val localTimeEvent: LocalTimeEvent) :
        CalendarEventOperationsState

    class ShowDecimalEventRequested(val decimalEvent: DecimalEvent) : CalendarEventOperationsState

    object AddDecimalEventRequested : CalendarEventOperationsState

    class RemoveDecimalEventRequested(val decimalEvent: DecimalEvent) : CalendarEventOperationsState

    class ShowEpgEventRequested(val epgEvent: LocalTimeEvent) : CalendarEventOperationsState

    object AddEpgEventRequested : CalendarEventOperationsState

    class RemoveEpgEventRequested(val epgEvent: LocalTimeEvent) : CalendarEventOperationsState
}