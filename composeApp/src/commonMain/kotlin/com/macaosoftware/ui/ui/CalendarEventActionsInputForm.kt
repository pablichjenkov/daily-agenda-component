package com.macaosoftware.ui.ui

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

        CalendarEventOperationsState.Hidden -> {
            // no-op
        }

        is CalendarEventOperationsState.ShowTimedEventRequested -> {
            val timeEvent = calendarEventOperationsState.localTimeEvent
            ModalBottomSheet(
                onDismissRequest = { uiActionListener.dismissInputForm() },
                sheetState = sheetState
            ) {
                Column {
                    Text("Time Event!")
                    var textTitle by remember { mutableStateOf(timeEvent.title) }
                    var textDescription by remember { mutableStateOf(timeEvent.description) }
                    var textStartHour by remember { mutableStateOf(timeEvent.startTime.hour.toString()) }
                    var textStartMinute by remember { mutableStateOf(timeEvent.startTime.minute.toString()) }
                    var textEndHour by remember { mutableStateOf(timeEvent.endTime.hour.toString()) }
                    var textEndMinute by remember { mutableStateOf(timeEvent.endTime.minute.toString()) }
                    TextField(
                        value = textTitle,
                        onValueChange = { newText -> textTitle = newText },
                        label = { Text("Title") }
                    )
                    TextField(
                        value = textDescription,
                        onValueChange = { newText -> textDescription = newText },
                        label = { Text("Description") }
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
//                        Button(onClick = {
//                            scope.launch { sheetState.hide() }.invokeOnCompletion {
//                                uiActionListener.confirmedAddTimeEvent(
//                                    title = textTitle,
//                                    startLocalTime = LocalTime(
//                                        textStartHour.toInt(),
//                                        textStartMinute.toInt()
//                                    ),
//                                    endLocalTime = LocalTime(
//                                        textEndHour.toInt(),
//                                        textEndMinute.toInt()
//                                    )
//                                )
//                            }
//                        }) {
//                            Text("Save")
//                        }
                        Button(onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                uiActionListener.dismissInputForm()
                            }
                        }) {
                            Text("Ok")
                        }
                    }
                }
            }
        }

        CalendarEventOperationsState.AddTimedEventRequested -> {
            ModalBottomSheet(
                onDismissRequest = { uiActionListener.dismissInputForm() },
                sheetState = sheetState
            ) {
                // Content of the bottom sheet
                // For example, a Column with some text
                Column {
                    Text("Add a new Time Event!")
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
                                try {
                                    uiActionListener.confirmedAddTimeEvent(
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
                                } catch (ex: Exception) {
                                    ex.printStackTrace()
                                }
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

        is CalendarEventOperationsState.RemoveTimedEventRequested -> {
            val eventTitleInitialValue = calendarEventOperationsState.localTimeEvent.title
            ModalBottomSheet(
                onDismissRequest = { uiActionListener.dismissInputForm() },
                sheetState = sheetState
            ) {
                var eventTitle by remember { mutableStateOf(eventTitleInitialValue) }
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
                                uiActionListener.confirmedRemoveTimeEvent(eventTitle)
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

        is CalendarEventOperationsState.ShowDecimalEventRequested -> {
            val decimalEvent = calendarEventOperationsState.decimalEvent
            ModalBottomSheet(
                onDismissRequest = { uiActionListener.dismissInputForm() },
                sheetState = sheetState
            ) {
                Column {
                    Text("Time Event!")
                    var textTitle by remember { mutableStateOf(decimalEvent.title) }
                    var textDescription by remember { mutableStateOf(decimalEvent.description) }
                    var textStartValue by remember { mutableStateOf(decimalEvent.startValue.toString()) }
                    var textEndValue by remember { mutableStateOf(decimalEvent.endValue.toString()) }
                    TextField(
                        value = textTitle,
                        onValueChange = { newText -> textTitle = newText },
                        label = { Text("Title") }
                    )
                    TextField(
                        value = textDescription,
                        onValueChange = { newText -> textDescription = newText },
                        label = { Text("Description") }
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
//                        Button(onClick = {
//                            scope.launch { sheetState.hide() }.invokeOnCompletion {
//                                uiActionListener.confirmedAddTimeEvent(
//                                    title = textTitle,
//                                    startLocalTime = LocalTime(
//                                        textStartHour.toInt(),
//                                        textStartMinute.toInt()
//                                    ),
//                                    endLocalTime = LocalTime(
//                                        textEndHour.toInt(),
//                                        textEndMinute.toInt()
//                                    )
//                                )
//                            }
//                        }) {
//                            Text("Save")
//                        }
                        Button(onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                uiActionListener.dismissInputForm()
                            }
                        }) {
                            Text("Ok")
                        }
                    }
                }
            }
        }

        CalendarEventOperationsState.AddDecimalEventRequested -> {
            ModalBottomSheet(
                onDismissRequest = { uiActionListener.dismissInputForm() },
                sheetState = sheetState
            ) {
                var textValueTitle by remember { mutableStateOf("") }
                var textStartValue by remember { mutableStateOf("") }
                var textEndValue by remember { mutableStateOf("") }
                Column {
                    Text("Add a new Decimal Event")
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
                                try {
                                    uiActionListener.confirmedAddDecimalSegment(
                                        title = textValueTitle,
                                        startValue = textStartValue.toFloat(),
                                        endValue = textEndValue.toFloat()
                                    )
                                } catch (ex: Exception) {
                                    ex.printStackTrace()
                                }
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

        is CalendarEventOperationsState.RemoveDecimalEventRequested -> {
            val eventTitleInitialValue = calendarEventOperationsState.decimalEvent.title
            ModalBottomSheet(
                onDismissRequest = { uiActionListener.dismissInputForm() },
                sheetState = sheetState
            ) {
                var eventTitle by remember { mutableStateOf(eventTitleInitialValue) }
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
                                println("Pablo Remove Event Clicked")
                                uiActionListener.confirmedRemoveDecimalEvent(eventTitle)
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

        is CalendarEventOperationsState.ShowEpgEventRequested -> {
            val timeEvent = calendarEventOperationsState.epgEvent
            ModalBottomSheet(
                onDismissRequest = { uiActionListener.dismissInputForm() },
                sheetState = sheetState
            ) {
                Column {
                    Text("EPG Event!")
                    var textTitle by remember { mutableStateOf(timeEvent.title) }
                    var textDescription by remember { mutableStateOf(timeEvent.description) }
                    var textStartHour by remember { mutableStateOf(timeEvent.startTime.hour.toString()) }
                    var textStartMinute by remember { mutableStateOf(timeEvent.startTime.minute.toString()) }
                    var textEndHour by remember { mutableStateOf(timeEvent.endTime.hour.toString()) }
                    var textEndMinute by remember { mutableStateOf(timeEvent.endTime.minute.toString()) }
                    TextField(
                        value = textTitle,
                        onValueChange = { newText -> textTitle = newText },
                        label = { Text("Title") }
                    )
                    TextField(
                        value = textDescription,
                        onValueChange = { newText -> textDescription = newText },
                        label = { Text("Description") }
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
//                        Button(onClick = {
//                            scope.launch { sheetState.hide() }.invokeOnCompletion {
//                                uiActionListener.confirmedAddTimeEvent(
//                                    title = textTitle,
//                                    startLocalTime = LocalTime(
//                                        textStartHour.toInt(),
//                                        textStartMinute.toInt()
//                                    ),
//                                    endLocalTime = LocalTime(
//                                        textEndHour.toInt(),
//                                        textEndMinute.toInt()
//                                    )
//                                )
//                            }
//                        }) {
//                            Text("Save")
//                        }
                        Button(onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                uiActionListener.dismissInputForm()
                            }
                        }) {
                            Text("Ok")
                        }
                    }
                }
            }
        }

        CalendarEventOperationsState.AddEpgEventRequested -> {
            ModalBottomSheet(
                onDismissRequest = { uiActionListener.dismissInputForm() },
                sheetState = sheetState
            ) {
                // Content of the bottom sheet
                // For example, a Column with some text
                Column {
                    Text("Add a new Show!")
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
                                try {
                                    uiActionListener.confirmedAddTimeEvent(
                                        title = textValueTitle,
                                        startLocalTime = LocalTime(
                                            hour = textStartHour.toInt(),
                                            minute = textStartMinute.toInt()
                                        ),
                                        endLocalTime = LocalTime(
                                            hour = textEndHour.toInt(),
                                            minute = textEndMinute.toInt()
                                        )
                                    )
                                } catch (ex: Exception) {
                                    ex.printStackTrace()
                                }
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

        is CalendarEventOperationsState.RemoveEpgEventRequested -> {
            val eventTitleInitialValue = calendarEventOperationsState.epgEvent.title
            ModalBottomSheet(
                onDismissRequest = { uiActionListener.dismissInputForm() },
                sheetState = sheetState
            ) {
                var eventTitle by remember { mutableStateOf(eventTitleInitialValue) }
                Column {
                    Text("Remove EPG Event")
                    TextField(
                        value = eventTitle,
                        onValueChange = { newText -> eventTitle = newText },
                        label = { Text("Title") }
                    )
                    Row {
                        Button(onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                uiActionListener.confirmedRemoveTimeEvent(eventTitle)
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
