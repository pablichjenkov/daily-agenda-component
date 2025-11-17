package com.macaosoftware.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarEventActionsInputForm(
    calendarEventOperationsState: CalendarEventOperationsState,
    uiActionListener: DayScheduleAppViewModel.UiActionListener
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    when (calendarEventOperationsState) {
        CalendarEventOperationsState.AddTimedEventRequested -> {
            ModalBottomSheet(
                onDismissRequest = { uiActionListener.dismissInputForm() },
                sheetState = sheetState
            ) {
                // Content of the bottom sheet
                // For example, a Column with some text
                Column {
                    Text("Add a new Event!")
                    var textValueTitle by remember { mutableStateOf("") }
                    var textStartHour by remember { mutableStateOf("") }
                    var textStartMinute by remember { mutableStateOf("") }
                    var textEndHour by remember { mutableStateOf("") }
                    var textEndMinute by remember { mutableStateOf("") }
                    TextField(
                        value = textValueTitle, // Can share the same state or use a separate one
                        onValueChange = { newText -> textValueTitle = newText },
                        label = { Text("Title") }
                    )
                    Row {
                        Column {
                            TextField(
                                value = textStartHour, // Can share the same state or use a separate one
                                onValueChange = { newText -> textStartHour = newText },
                                label = { Text("Hour") }
                            )
                            TextField(
                                value = textStartMinute, // Can share the same state or use a separate one
                                onValueChange = { newText -> textStartMinute = newText },
                                label = { Text("Minute") }
                            )
                        }
                        Column {
                            TextField(
                                value = textEndHour, // Can share the same state or use a separate one
                                onValueChange = { newText -> textEndHour = newText },
                                label = { Text("Hour") }
                            )
                            TextField(
                                value = textEndMinute, // Can share the same state or use a separate one
                                onValueChange = { newText -> textEndMinute = newText },
                                label = { Text("Minute") }
                            )
                        }
                    }
                    Row {
                        Button(onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                uiActionListener.confirmAddTimeEvent(
                                    title = textValueTitle,
                                    startLocalTime = LocalTime(
                                        textStartHour.toInt(),
                                        textStartMinute.toInt()
                                    ),
                                    endLocalTime = LocalTime(
                                        textEndHour.toInt(),
                                        textEndMinute.toInt()
                                    )
                                )
                            }
                        }) {
                            Text("Add")
                        }
                        Button(onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                uiActionListener.dismissInputForm()
                            }
                        }) {
                            Text("Cancel")
                        }
                    }
                }
            }
        }

        CalendarEventOperationsState.Hidden -> {
            // no-op
        }

        CalendarEventOperationsState.AddDecimalSegmentRequested -> {
            ModalBottomSheet(
                onDismissRequest = { uiActionListener.dismissInputForm() },
                sheetState = sheetState
            ) {
                var textValueTitle by remember { mutableStateOf("") }
                var textStartValue by remember { mutableStateOf("") }
                var textEndValue by remember { mutableStateOf("") }
                Column {
                    Text("Add a new Event")
                    TextField(
                        value = textValueTitle, // Can share the same state or use a separate one
                        onValueChange = { newText -> textValueTitle = newText },
                        label = { Text("Title") }
                    )
                    Column {
                        TextField(
                            value = textStartValue, // Can share the same state or use a separate one
                            onValueChange = { newText -> textStartValue = newText },
                            label = { Text("Start Value") }
                        )
                        TextField(
                            value = textEndValue, // Can share the same state or use a separate one
                            onValueChange = { newText -> textEndValue = newText },
                            label = { Text("End Value") }
                        )
                    }
                    Row {
                        Button(onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                uiActionListener.confirmAddDecimalSegment(
                                    title = textValueTitle,
                                    startValue = textStartValue.toFloat(),
                                    endValue = textEndValue.toFloat()
                                )
                            }
                        }) {
                            Text("Add")
                        }
                        Button(onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                uiActionListener.dismissInputForm()
                            }
                        }) {
                            Text("Cancel")
                        }
                    }
                }
            }
        }

        CalendarEventOperationsState.RemoveDecimalSegmentRequested -> {
            ModalBottomSheet(
                onDismissRequest = { uiActionListener.dismissInputForm() },
                sheetState = sheetState
            ) {
                var eventTitle by remember { mutableStateOf("") }
                Column {
                    Text("Remove Event")
                    TextField(
                        value = eventTitle,
                        onValueChange = { newText -> eventTitle = newText },
                        label = { Text("Title") }
                    )
                    Row {
                        Button(onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                uiActionListener.confirmRemoveDecimalSegment(eventTitle)
                            }
                        }) {
                            Text("Remove")
                        }
                        Button(onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                uiActionListener.dismissInputForm()
                            }
                        }) {
                            Text("Cancel")
                        }
                    }
                }
            }
        }

        CalendarEventOperationsState.RemoveTimedEventRequested -> {
            ModalBottomSheet(
                onDismissRequest = { uiActionListener.dismissInputForm() },
                sheetState = sheetState
            ) {
                var eventTitle by remember { mutableStateOf("") }
                Column {
                    Text("Remove Event")
                    TextField(
                        value = eventTitle,
                        onValueChange = { newText -> eventTitle = newText },
                        label = { Text("Title") }
                    )
                    Row {
                        Button(onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                uiActionListener.confirmRemoveTimeEvent(eventTitle)
                            }
                        }) {
                            Text("Remove")
                        }
                        Button(onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                uiActionListener.dismissInputForm()
                            }
                        }) {
                            Text("Cancel")
                        }
                    }
                }
            }
        }
    }
}
