package com.macaosoftware.ui.dailyagenda

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun TimeSlotsView(
    timeSlotsStateController: TimeSlotsStateController,
    eventContentProvider: @Composable (event: LocalTimeEvent) -> Unit
) {

    val dailyAgendaState = timeSlotsStateController.dailyAgendaStateController.state.value
    val scrollState = rememberScrollState()
    val currentTimeMarkerStateController = remember {
        CurrentTimeMarkerStateController(dailyAgendaState = dailyAgendaState)
    }
    Box(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState)
    ) {
        SlotsLayer(dailyAgendaState = dailyAgendaState)
        DailyAgendaRootLayout(
            dailyAgendaState = dailyAgendaState,
            eventContentProvider = { event ->
                // Intercept the event to apply the toLocalTimeEvent() transformation
                eventContentProvider.invoke(event.toLocalTimeEvent())
            }
        )
        CurrentTimeMarkerView(currentTimeMarkerStateController)
    }
}
