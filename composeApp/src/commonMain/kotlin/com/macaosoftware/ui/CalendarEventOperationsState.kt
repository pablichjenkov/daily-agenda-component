package com.macaosoftware.ui

sealed interface CalendarEventOperationsState {
    object Hidden : CalendarEventOperationsState
    object AddTimedEventRequested : CalendarEventOperationsState
    object RemoveTimedEventRequested : CalendarEventOperationsState
    object AddDecimalSegmentRequested : CalendarEventOperationsState
    object RemoveDecimalSegmentRequested : CalendarEventOperationsState
}