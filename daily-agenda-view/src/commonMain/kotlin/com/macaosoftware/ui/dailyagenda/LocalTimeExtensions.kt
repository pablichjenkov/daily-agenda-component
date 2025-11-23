package com.macaosoftware.ui.dailyagenda

import kotlinx.datetime.LocalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

private const val HOUR_AM = "AM"
private const val HOUR_PM = "PM"
private const val MINUTES_IN_ONE_HOUR = 60

@OptIn(ExperimentalUuidApi::class)
data class LocalTimeEvent(
    val uuid: Uuid,
    val title: String,
    val description: String,
    val startTime: LocalTime,
    val endTime: LocalTime
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LocalTimeEvent) return false
        return other.uuid == uuid
    }

    override fun hashCode(): Int {
        return uuid.hashCode()
    }
}

data class LocalTimeSlot(
    val title: String,
    val startTime: LocalTime,
    val endTime: LocalTime
)

@OptIn(ExperimentalUuidApi::class)
fun DecimalEvent.toLocalTimeEvent(): LocalTimeEvent {
    val startLocalTime = fromValueToLocalTime(value = startValue)
    val endLocalTime = fromValueToLocalTime(value = endValue)
    return LocalTimeEvent(
        uuid = uuid,
        title = title,
        description = description,
        startTime = startLocalTime,
        endTime = endLocalTime
    )
}

@OptIn(ExperimentalUuidApi::class)
fun LocalTimeEvent.toDecimalSegment(): DecimalEvent {
    val startTimeValue = fromLocalTimeToValue(localTime = startTime)
    val endTimeValue = fromLocalTimeToValue(localTime = endTime)
    return DecimalEvent(
        uuid = uuid,
        title = title,
        description = description,
        startValue = startTimeValue,
        endValue = endTimeValue
    )
}

fun Slot.toLocalTimeSlot(): LocalTimeSlot {
    return LocalTimeSlot(
        title = title,
        startTime = fromValueToLocalTime(value = startValue),
        endTime = fromValueToLocalTime(value = endValue),
    )
}

fun LocalTimeSlot.toSlot(): Slot {
    return Slot(
        title = title,
        startValue = fromLocalTimeToValue(localTime = startTime),
        endValue = fromLocalTimeToValue(localTime = endTime)
    )
}

data class TimeSlotConfig(
    val startSlotTime: LocalTime = LocalTime(0, 0),
    val endSlotTime: LocalTime = LocalTime(23, 59),
    val useAmPm: Boolean = true,
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
    useAmPm: Boolean
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
    val hourUnitsFormatted = hourUnits.takeIf { it != 0 } ?: 12
    val amPmSuffix = if (slotStartValue < 12F) HOUR_AM else HOUR_PM
    return "$hourUnitsFormatted:$minutesTwoDigitFormat:$amPmSuffix"
}

fun TimeSlotConfig.toSlotConfig(): SlotConfig {
    return SlotConfig(
        initialSlotValue = fromLocalTimeToValue(startSlotTime),
        lastSlotValue = fromLocalTimeToValue(endSlotTime),
        slotScale = slotScale,
        slotHeight = slotHeight,
        timelineLeftPadding = timelineLeftPadding
    )
}

fun Config.toSlotConfig(): SlotConfig {
    return SlotConfig(
        initialSlotValue = initialSlotValue,
        lastSlotValue = lastSlotValue,
        slotScale = slotScale,
        slotHeight = slotHeight,
        timelineLeftPadding = timelineLeftPadding
    )
}
