package com.macaosoftware.ui

import com.macaosoftware.ui.dailyagenda.DecimalEvent
import com.macaosoftware.ui.dailyagenda.LocalTimeEvent

sealed interface CalendarEventOperationsState {
    object Hidden : CalendarEventOperationsState

    object AddTimedEventRequested : CalendarEventOperationsState

    sealed interface RemoveTimedEventRequested : CalendarEventOperationsState {
        object Empty : RemoveTimedEventRequested
        class WithEvent(val localTimeEvent: LocalTimeEvent) : RemoveTimedEventRequested
    }

    object AddDecimalEventRequested : CalendarEventOperationsState

    sealed interface RemoveDecimalEventRequested : CalendarEventOperationsState {
        object Empty : RemoveDecimalEventRequested
        class WithEvent(val decimalEvent: DecimalEvent) : RemoveDecimalEventRequested
    }
}