package com.macaosoftware.ui.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.CalendarEventActionsView(
    showTimeSlots: Boolean,
    uiActionListener: DayScheduleAppViewModel.UiActionListener
) {
    Row(
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(8.dp)
            .wrapContentSize(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FloatingActionButton(
            modifier = Modifier.wrapContentSize(),
            onClick = {
                if (showTimeSlots) {
                    uiActionListener.showAddTimeEventForm()
                } else {
                    uiActionListener.showAddDecimalSegmentForm()
                }
            }
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Add"
            )
        }
        FloatingActionButton(
            modifier = Modifier.wrapContentSize(),
            onClick = {
                if (showTimeSlots) {
                    uiActionListener.showRemoveTimeEventForm()
                } else {
                    uiActionListener.showRemoveDecimalEventForm()
                }
            }
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Remove"
            )
        }
        FloatingActionButton(
            modifier = Modifier.wrapContentSize(),
            onClick = { uiActionListener.toggleAxisType() }
        ) {
            if (showTimeSlots) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Decimal Axis"
                )
            } else {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Time Axis"
                )
            }
        }
    }
}
