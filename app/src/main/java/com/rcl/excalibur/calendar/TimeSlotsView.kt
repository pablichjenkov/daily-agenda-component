package com.rcl.excalibur.calendar

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
fun SlotsLayer(calendarState: CalendarState) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        calendarState.slotToEventMap.keys.forEach { slot ->
            SlotLine(slot = slot)
        }
    }
}

@Composable
fun SlotLine(slot: Slot) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = SlotHeight.dp)
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
