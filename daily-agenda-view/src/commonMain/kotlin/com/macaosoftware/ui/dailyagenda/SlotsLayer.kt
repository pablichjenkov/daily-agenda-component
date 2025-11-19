package com.macaosoftware.ui.dailyagenda

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
internal fun SlotsLayer(dailyAgendaState: DailyAgendaState) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        dailyAgendaState.slotToDecimalEventMap.keys.forEach { slot ->
            SlotLine(slot = slot, config = dailyAgendaState.config)
        }
    }
}

@Composable
private fun SlotLine(slot: Slot, config: Config) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = config.slotHeight.dp)
    ) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            color = Color.Black
        )
        Text(text = slot.title)
    }
}
