package com.macaosoftware.ui.dailyagenda

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp

@Composable
internal fun ChannelSlotsLayout(
    channelSlotsState: ChannelSlotsState,
    eventContentProvider: @Composable (localTimeEvent: LocalTimeEvent) -> Unit
) {
    Row {
        channelSlotsState.channels.forEach { channel ->
            ChannelColumn(channel)
        }
    }

}

@Composable
fun ChannelColumn(channel: Channel) {
    Box {

    }
}