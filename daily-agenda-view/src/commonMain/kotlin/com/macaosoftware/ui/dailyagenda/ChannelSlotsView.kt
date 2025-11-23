package com.macaosoftware.ui.dailyagenda

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun ChannelSlotsView(
    channelSlotsStateController: ChannelSlotsStateController,
    eventContentProvider: @Composable (localTimeEvent: LocalTimeEvent) -> Unit
) {
    val channelSlotsState = channelSlotsStateController.state.value
    val scrollState = rememberScrollState()
    val currentTimeMarkerStateController = remember {
        CurrentTimeMarkerStateController(slotConfig = channelSlotsState.channelSlotsConfig.toSlotConfig())
    }
    Box(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState)
    ) {
        SlotsLayer(slotsLayerState = channelSlotsState.getSlotsLayerState())
        ChannelSlotsLayout(
            channelSlotsState = channelSlotsState,
            eventContentProvider = { localTimeEvent ->
                // Intercept the event to apply the toLocalTimeEvent() transformation
                eventContentProvider.invoke(localTimeEvent)
            }
        )
        CurrentTimeMarkerView(currentTimeMarkerStateController)
    }
}