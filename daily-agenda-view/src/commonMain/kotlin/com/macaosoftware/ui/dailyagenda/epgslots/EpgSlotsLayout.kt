package com.macaosoftware.ui.dailyagenda.epgslots

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.macaosoftware.ui.dailyagenda.timeslots.LocalTimeEvent
import com.macaosoftware.ui.dailyagenda.timeslots.fromLocalTimeToValue
import com.macaosoftware.ui.dailyagenda.timeslots.toSlotConfig

@Composable
internal fun ChannelSlotsLayout(
    epgSlotsState: EpgSlotsState,
    eventContentProvider: @Composable (localTimeEvent: LocalTimeEvent) -> Unit
) {
    val timelineLeftPadding = epgSlotsState.epgChannelSlotConfig.timeSlotConfig.timelineLeftPadding
    val channelWidth = epgSlotsState.epgChannelSlotConfig.channelWidth
    val topHeaderHeight = epgSlotsState.epgChannelSlotConfig.topHeaderHeight
    Row(modifier = Modifier.padding(start = timelineLeftPadding.dp).fillMaxSize()) {
        epgSlotsState.epgChannels.forEach { channel ->
            Column(
                modifier = Modifier.width(width = channelWidth.dp).fillMaxHeight()
            ) {
                Box(
                    Modifier.height(height = topHeaderHeight.dp).fillMaxWidth()
                        .padding(all = 2.dp).background(color = Color.LightGray)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = channel.name
                    )
                }
                ChannelColumn(
                    epgChannel = channel,
                    epgChannelSlotConfig = epgSlotsState.epgChannelSlotConfig,
                    eventContentProvider = eventContentProvider
                )
            }
        }
    }
}

@Composable
fun ChannelColumn(
    epgChannel: EpgChannel,
    epgChannelSlotConfig: EpgChannelSlotConfig,
    eventContentProvider: @Composable (localTimeEvent: LocalTimeEvent) -> Unit
) {
    val slotsConfig = epgChannelSlotConfig.timeSlotConfig.toSlotConfig()
    val initialSlotValue = slotsConfig.initialSlotValue
    Box(modifier = Modifier.fillMaxSize()) {
        val scaleMultiplier = slotsConfig.slotHeight * slotsConfig.slotScale
        epgChannel.events.forEach { localTimeEvent ->
            val startValue = fromLocalTimeToValue(localTimeEvent.startTime)
            val endValue = fromLocalTimeToValue(localTimeEvent.endTime)
            val offsetY = (startValue - initialSlotValue) * scaleMultiplier
            val programHeight = (endValue - startValue) * scaleMultiplier
            Box(
                modifier = Modifier.offset(y = offsetY.dp)
                    .fillMaxWidth()
                    .height(height = programHeight.dp)

            ) { eventContentProvider.invoke(localTimeEvent) }
        }
    }
}
