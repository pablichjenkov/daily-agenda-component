package com.macaosoftware.ui.dailyagenda.epgslots

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.macaosoftware.ui.dailyagenda.timeslots.LocalTimeEvent
import com.macaosoftware.ui.dailyagenda.timeslots.fromLocalTimeToValue
import com.macaosoftware.ui.dailyagenda.timeslots.toSlotConfig

@Composable
internal fun ChannelSlotsLayout(
    epgSlotsState: EpgSlotsState,
    eventContentProvider: @Composable (localTimeEvent: LocalTimeEvent) -> Unit
) {
    Row(modifier = Modifier.fillMaxSize()) {
        epgSlotsState.epgChannels.forEach { channel ->
            ChannelColumn(
                channel,
                epgSlotsState.epgChannelSlotsConfig,
                eventContentProvider
            )
        }
    }

}

@Composable
fun ChannelColumn(
    epgChannel: EpgChannel,
    epgChannelSlotsConfig: EpgChannelSlotsConfig,
    eventContentProvider: @Composable (localTimeEvent: LocalTimeEvent) -> Unit
) {

    val channelWidth = epgChannelSlotsConfig.channelWidth
    val slotsConfig = epgChannelSlotsConfig.timeSlotConfig.toSlotConfig()

    Box(modifier = Modifier.width(width = channelWidth.dp).fillMaxHeight()) {



        epgChannel.events.forEach { localTimeEvent ->

            val startValue = fromLocalTimeToValue(localTimeEvent.startTime)
            val enValue = fromLocalTimeToValue(localTimeEvent.endTime)
            val offsetY = startValue * slotsConfig.slotHeight * slotsConfig.slotScale
            val height = (enValue - startValue) * slotsConfig.slotHeight * slotsConfig.slotScale

            Box(
                modifier = Modifier.offset(y = offsetY.dp)
                    .width(width = channelWidth.dp)
                    .height(height = height.dp)

            ) {
                eventContentProvider.invoke(localTimeEvent)
            }
        }

    }
}