package com.macaosoftware.ui.dailyagenda

import kotlinx.datetime.LocalTime

data class HourAndMinute(val hour: Int, val minute: Int)

class HourAndMinuteEvent(
    val startSlot: Slot,
    val title: String,
    val startTime: HourAndMinute,
    val endTime: HourAndMinute
)

class LocalTimeEvent(
    val startSlot: Slot,
    val title: String,
    val startTime: LocalTime,
    val endTime: LocalTime
)

fun Event.toHourAndMinuteEvent(): HourAndMinuteEvent {
    val startTimeHourAndMinute = fromValueToHourAndMinute(value = startTime)
    val endTimeHourAndMinute = fromValueToHourAndMinute(value = endTime)
    return HourAndMinuteEvent(
        startSlot = startSlot,
        title = title,
        startTime = startTimeHourAndMinute,
        endTime = endTimeHourAndMinute
    )
}

fun Event.toLocalTimeEvent(): LocalTimeEvent {
    val startLocalTime = fromValueToLocalTime(value = startTime)
    val endLocalTime = fromValueToLocalTime(value = endTime)
    return LocalTimeEvent(
        startSlot = startSlot,
        title = title,
        startTime = startLocalTime,
        endTime = endLocalTime
    )
}

fun HourAndMinuteEvent.toEvent(): Event {
    val startTimeValue = fromHourAndMinuteToValue(
        hour = startTime.hour,
        minute = startTime.minute
    )
    val endTimeValue = fromHourAndMinuteToValue(
        hour = endTime.hour,
        minute = endTime.minute
    )
    return Event(
        startSlot = startSlot,
        title = title,
        startTime = startTimeValue,
        endTime = endTimeValue
    )
}

fun LocalTimeEvent.toEvent(): Event {
    val startTimeValue = fromHourAndMinuteToValue(
        hour = startTime.hour,
        minute = startTime.minute
    )
    val endTimeValue = fromHourAndMinuteToValue(
        hour = endTime.hour,
        minute = endTime.minute
    )
    return Event(
        startSlot = startSlot,
        title = title,
        startTime = startTimeValue,
        endTime = endTimeValue
    )
}

fun fromHourAndMinuteToValue(hour: Int, minute: Int): Float {
    val minuteFraction = minute.toFloat() / 60
    return hour.toFloat() + minuteFraction
}

fun fromValueToHourAndMinute(value: Float): HourAndMinute {
    val remaining = value % 1
    val minutes = (remaining * 60).toInt()
    val hours = value.toInt()
    return HourAndMinute(hour = hours, minute = minutes)
}

fun fromValueToLocalTime(value: Float): LocalTime {
    val remaining = value % 1
    val minutes = (remaining * 60).toInt()
    val hours = value.toInt()
    return LocalTime(hour = hours, minute = minutes)
}

fun fromDecimalValueToTimeText(
    slotStartValue: Float,
    useAmPm: Boolean = true
): String {
    val remaining = slotStartValue % 1
    val minutes = (remaining * 60).toInt()
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
    val amPmSuffix = if (slotStartValue < 12F) "AM" else "PM"
    return "$hourUnitsFormatted:$minutesTwoDigitFormat:$amPmSuffix"
}
