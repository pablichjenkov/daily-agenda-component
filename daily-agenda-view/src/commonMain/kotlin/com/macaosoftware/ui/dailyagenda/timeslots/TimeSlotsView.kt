package com.macaosoftware.ui.dailyagenda.timeslots

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.macaosoftware.ui.dailyagenda.decimalslots.DecimalSlotsBaseLayout
import com.macaosoftware.ui.dailyagenda.marker.CurrentTimeMarkerStateController
import com.macaosoftware.ui.dailyagenda.marker.CurrentTimeMarkerView
import com.macaosoftware.ui.dailyagenda.slotslayer.SlotsLayer
import com.macaosoftware.ui.dailyagenda.slotslayer.getSlotsLayerState

@Composable
fun TimeSlotsView(
    timeSlotsStateController: TimeSlotsStateController,
    eventContentProvider: @Composable (event: LocalTimeEvent) -> Unit
) {
    val dailyAgendaState =
        timeSlotsStateController.decimalSlotsBaseLayoutStateController.state.value ?: return
    val scrollState = rememberScrollState()
    val currentTimeMarkerStateController = remember {
        CurrentTimeMarkerStateController(decimalSlotConfig = timeSlotsStateController.slotConfig)
    }
    Box(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState)
    ) {
        SlotsLayer(slotsLayerState = dailyAgendaState.getSlotsLayerState())
        DecimalSlotsBaseLayout(
            decimalSlotsBaseLayoutState = dailyAgendaState,
            eventContentProvider = { event ->
                // Intercept the event to apply the toLocalTimeEvent() transformation
                eventContentProvider.invoke(event.toLocalTimeEvent())
            }
        )
        CurrentTimeMarkerView(
            currentTimeMarkerStateController = currentTimeMarkerStateController
        )
    }
}
