package com.macaosoftware.ui.dailyagenda

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DecimalSlotsView(
    decimalSlotsStateController: DecimalSlotsStateController,
    eventContentProvider: @Composable (event: Event) -> Unit
) {

    val dailyAgendaState = decimalSlotsStateController.dailyAgendaStateController.state.value
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Box {
            SlotsLayer(dailyAgendaState = dailyAgendaState)
            DailyAgendaRootLayout(
                dailyAgendaState = dailyAgendaState,
                eventContentProvider = eventContentProvider
            )
        }
    }
}
