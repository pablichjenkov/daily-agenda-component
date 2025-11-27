package com.macaosoftware.ui.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.CalendarEventActionsView(
    slotsViewType: SlotsViewType,
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
                uiActionListener.showAddEventForm(slotsViewType)
            }
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Add Event"
            )
        }
        var expanded by remember { mutableStateOf(false) }
        FloatingActionButton(
            modifier = Modifier.wrapContentSize(),
            onClick = {
                expanded = true
            }
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                DropdownMenuItem(
                    text = { Text("Timeline Axis") },
                    onClick = {
                        expanded = false
                        uiActionListener.toggleAxisType(SlotsViewType.Timeline)
                    }
                )
                DropdownMenuItem(
                    text = { Text("Decimal Axis") },
                    onClick = {
                        expanded = false
                        uiActionListener.toggleAxisType(SlotsViewType.Decimal)
                    }
                )
                DropdownMenuItem(
                    text = { Text("Epg Axis") },
                    onClick = {
                        expanded = false
                        uiActionListener.toggleAxisType(SlotsViewType.Epg)
                    }
                )
            }
            val axisText = when (slotsViewType) {
                SlotsViewType.Decimal -> {
                    "Decimal Axis"
                }

                SlotsViewType.Timeline -> {
                    "Timeline Axis"
                }

                SlotsViewType.Epg -> {
                    "Epg Axis"
                }
            }
            Text(
                modifier = Modifier.padding(16.dp),
                text = axisText
            )
        }
    }
}
