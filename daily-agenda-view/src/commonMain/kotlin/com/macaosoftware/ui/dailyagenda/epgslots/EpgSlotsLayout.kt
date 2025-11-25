package com.macaosoftware.ui.dailyagenda.epgslots

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.macaosoftware.ui.dailyagenda.timeslots.LocalTimeEvent

@Composable
internal fun ChannelSlotsLayout(
    epgChannelSlotsState: EpgChannelSlotsState,
    eventContentProvider: @Composable (localTimeEvent: LocalTimeEvent) -> Unit
) {
    Row {
        epgChannelSlotsState.epgChannels.forEach { channel ->
            ChannelColumn(channel)
        }
    }

}

@Composable
fun ChannelColumn(epgChannel: EpgChannel) {
    Box {

    }
}