package com.macaosoftware.ui.dailyagenda.decimalslots

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.macaosoftware.ui.dailyagenda.slotslayer.SlotsLayer
import com.macaosoftware.ui.dailyagenda.slotslayer.getSlotsLayerState

@Composable
fun DecimalSlotsView(
    decimalSlotsStateController: DecimalSlotsStateController,
    eventContentProvider: @Composable (decimalEvent: DecimalEvent) -> Unit
) {
    val dailyAgendaState = decimalSlotsStateController.decimalSlotsBaseLayoutStateController.state.value
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        SlotsLayer(slotsLayerState = dailyAgendaState.getSlotsLayerState())
        DecimalSlotsBaseLayout(
            decimalSlotsBaseLayoutState = dailyAgendaState,
            eventContentProvider = eventContentProvider
        )
    }
}
