package com.macaosoftware.ui.dailyagenda

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DecimalSlotsView(
    decimalSlotsStateController: DecimalSlotsStateController,
    eventContentProvider: @Composable (decimalEvent: DecimalEvent) -> Unit
) {
    val dailyAgendaState = decimalSlotsStateController.dailyAgendaStateController.state.value
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        SlotsLayer(slotsLayerState = dailyAgendaState.getSlotsLayerState())
        DailyAgendaRootLayout(
            dailyAgendaState = dailyAgendaState,
            eventContentProvider = eventContentProvider
        )
    }
}
