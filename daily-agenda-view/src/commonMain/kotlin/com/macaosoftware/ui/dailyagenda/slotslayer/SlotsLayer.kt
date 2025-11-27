package com.macaosoftware.ui.dailyagenda.slotslayer

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
import com.macaosoftware.ui.dailyagenda.decimalslots.Slot


@Composable
internal fun SlotsLayer(
    modifier: Modifier = Modifier,
    slotsLayerState: SlotsLayerState
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        slotsLayerState.slots.forEach { slot ->
            SlotLine(slot = slot, slotHeight = slotsLayerState.slotHeight)
        }
    }
}

@Composable
private fun SlotLine(slot: Slot, slotHeight: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = slotHeight.dp)
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
