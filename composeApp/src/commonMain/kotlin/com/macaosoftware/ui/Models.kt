package com.macaosoftware.ui

import com.macaosoftware.ui.dailyagenda.LocalTimeEvent
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class AllDayEvent(
    val uuid: Uuid,
    val title: String,
    val description: String
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