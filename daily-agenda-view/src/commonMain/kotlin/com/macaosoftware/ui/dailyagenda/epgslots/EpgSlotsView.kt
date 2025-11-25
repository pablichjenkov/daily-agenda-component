package com.macaosoftware.ui.dailyagenda.epgslots

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.macaosoftware.ui.dailyagenda.marker.CurrentTimeMarkerStateController
import com.macaosoftware.ui.dailyagenda.marker.CurrentTimeMarkerView
import com.macaosoftware.ui.dailyagenda.timeslots.LocalTimeEvent
import com.macaosoftware.ui.dailyagenda.slotslayer.SlotsLayer
import com.macaosoftware.ui.dailyagenda.slotslayer.getSlotsLayerState

@Composable
fun EpgSlotsView(
    epgSlotsStateController: EpgSlotsStateController,
    eventContentProvider: @Composable (localTimeEvent: LocalTimeEvent) -> Unit
) {
    val channelSlotsState = epgSlotsStateController.state.value
    val scrollState = rememberScrollState()
    val currentTimeMarkerStateController = remember {
        CurrentTimeMarkerStateController(slotConfig = channelSlotsState.epgChannelSlotsConfig.toSlotConfig())
    }
    Box(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState)
    ) {
        SlotsLayer(slotsLayerState = channelSlotsState.getSlotsLayerState())
        ChannelSlotsLayout(
            epgChannelSlotsState = channelSlotsState,
            eventContentProvider = { localTimeEvent ->
                // Intercept the event to apply the toLocalTimeEvent() transformation
                eventContentProvider.invoke(localTimeEvent)
            }
        )
        CurrentTimeMarkerView(currentTimeMarkerStateController)
    }
}