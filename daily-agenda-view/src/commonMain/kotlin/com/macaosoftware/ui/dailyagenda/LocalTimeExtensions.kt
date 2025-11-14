package com.macaosoftware.ui.dailyagenda

import kotlinx.datetime.LocalTime

private const val HOUR_AM = "AM"
private const val HOUR_PM = "PM"
private const val MINUTES_IN_ONE_HOUR = 60

data class LocalTimeEvent(
    val title: String,
    val startTime: LocalTime,
    val endTime: LocalTime
)

data class LocalTimeSlot(
    val title: String,
    val localTime: LocalTime
)

fun Event.toLocalTimeEvent(): LocalTimeEvent {
    val startLocalTime = fromValueToLocalTime(value = startValue)
    val endLocalTime = fromValueToLocalTime(value = endValue)
    return LocalTimeEvent(
        title = title,
        startTime = startLocalTime,
        endTime = endLocalTime
    )
}

fun LocalTimeEvent.toEvent(): Event {
    val startTimeValue = fromLocalTimeToValue(localTime = startTime)
    val endTimeValue = fromLocalTimeToValue(localTime = endTime)
    return Event(
        title = title,
        startValue = startTimeValue,
        endValue = endTimeValue
    )
}

fun Slot.toLocalTimeSlot(): LocalTimeSlot {
    return LocalTimeSlot(
        title = title,
        localTime = fromValueToLocalTime(value = value)
    )
}

fun LocalTimeSlot.toSlot(): Slot {
    return Slot(
        title = title,
        value = fromLocalTimeToValue(localTime = localTime)
    )
}

data class TimeSlotConfig(
    val startSlotTime: LocalTime = LocalTime(0, 0),
    val endSlotTime: LocalTime = LocalTime(23, 59),
    val slotScale: Int = 2,
    val slotHeight: Int = 48,
    val timelineLeftPadding: Int = 72
)

internal fun fromLocalTimeToValue(localTime: LocalTime): Float {
    val minuteFraction = localTime.minute.toFloat() / MINUTES_IN_ONE_HOUR
    return localTime.hour.toFloat() + minuteFraction
}

fun fromValueToLocalTime(value: Float): LocalTime {
    val remaining = value % 1
    val minutes = (remaining * MINUTES_IN_ONE_HOUR).toInt()
    val hours = value.toInt()
    return LocalTime(hour = hours, minute = minutes)
}

fun fromDecimalValueToTimeText(
    slotStartValue: Float,
    useAmPm: Boolean = true
): String {
    val remaining = slotStartValue % 1
    val minutes = (remaining * MINUTES_IN_ONE_HOUR).toInt()
    val minutesTwoDigitFormat = if (minutes > 9) {
        minutes.toString()
    } else {
        "0$minutes"
    }

    if (!useAmPm) {
        val units = slotStartValue.toInt()
        return "$units:$minutesTwoDigitFormat"
    }

    val slotStartValueInt = slotStartValue.toInt()
    val hourUnits = slotStartValueInt % 12
    val hourUnitsFormatted = hourUnits.takeIf { it != 0 } ?: slotStartValueInt
    val amPmSuffix = if (slotStartValue < 12F) HOUR_AM else HOUR_PM
    return "$hourUnitsFormatted:$minutesTwoDigitFormat:$amPmSuffix"
}

fun TimeSlotConfig.toSlotConfig() : SlotConfig {
    return SlotConfig(
        initialSlotValue = fromLocalTimeToValue(startSlotTime),
        lastSlotValue = fromLocalTimeToValue(endSlotTime),
        slotScale = slotScale,
        slotHeight = slotHeight,
        timelineLeftPadding = timelineLeftPadding
    )
}
