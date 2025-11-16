package com.macaosoftware.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.macaosoftware.ui.dailyagenda.DecimalSlotsStateController
import com.macaosoftware.ui.dailyagenda.DecimalSlotsView
import com.macaosoftware.ui.dailyagenda.EventWidthType
import com.macaosoftware.ui.dailyagenda.EventsArrangement
import com.macaosoftware.ui.dailyagenda.SlotConfig
import com.macaosoftware.ui.dailyagenda.TimeSlotsStateController
import com.macaosoftware.ui.dailyagenda.TimeSlotsView
import com.macaosoftware.ui.data.Sample1
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.random.Random

@Composable
@Preview
fun DayScheduleApp() {
    val viewModel = remember { DayScheduleAppViewModel() }
    MaterialTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = innerPadding)
            ) {
                if (viewModel.showTimeSlots.value) {
                    TimeSlotExample(viewModel.timeSlotsStateController)
                } else {
                    DecimalSlotExample(viewModel.decimalSlotsStateController)
                }
                CalendarEventActionsView(
                    showTimeSlots = viewModel.showTimeSlots.value,
                    uiActionListener = viewModel.uiActionListener
                )
                CalendarEventActionsInputForm(
                    calendarEventOperationsState = viewModel.calendarEventOperationsState.value,
                    uiActionListener = viewModel.uiActionListener
                )
            }
        }
    }
}

@Composable
private fun TimeSlotExample(
    timeSlotsStateController: TimeSlotsStateController
) {
    Box(modifier = Modifier.fillMaxSize()) {
        TimeSlotsView(timeSlotsStateController = timeSlotsStateController) { localTimeEvent ->
            Box(
                modifier = Modifier.fillMaxSize()
                    .padding(all = 2.dp)
                    .background(color = generateRandomColor())
            ) {
                Text(
                    text = "${localTimeEvent.title}: ${localTimeEvent.startTime}-${localTimeEvent.endTime}",
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun DecimalSlotExample(
    decimalSlotsStateController: DecimalSlotsStateController
) {
    Box(modifier = Modifier.fillMaxSize()) {
        DecimalSlotsView(
            decimalSlotsStateController = decimalSlotsStateController
        ) { event ->
            Box(
                modifier = Modifier.fillMaxSize()
                    .padding(all = 2.dp)
                    .background(color = generateRandomColor())
            ) {
                Text(
                    text = "${event.title}: ${event.startValue}-${event.endValue}",
                    fontSize = 12.sp
                )
            }
        }

    }
}

@Composable
fun BoxScope.CalendarEventActionsView(
    showTimeSlots: Boolean,
    uiActionListener: DayScheduleAppViewModel.UiActionListener
) {
    Row(
        modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp).wrapContentSize()
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
            onClick = { }
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
                onDismissRequest = {
                    uiActionListener.dismissInputForm()
                },
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
                                if (!sheetState.isVisible) {
                                    // showBottomSheet = false
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
                onDismissRequest = {
                    uiActionListener.dismissInputForm()
                },
                sheetState = sheetState
            ) {
                Text("Add a new Event!")
                var textValueTitle by remember { mutableStateOf("") }
                var textStartValue by remember { mutableStateOf("") }
                var textEndValue by remember { mutableStateOf("") }
                Column {
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
                                if (!sheetState.isVisible) {
                                    // showBottomSheet = false
                                    uiActionListener.confirmAddDecimalSegment(
                                        title = textValueTitle,
                                        startValue = textStartValue.toFloat(),
                                        endValue = textEndValue.toFloat()
                                    )
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

        CalendarEventOperationsState.RemoveDecimalSegmentRequested -> {
            ModalBottomSheet(
                onDismissRequest = {
                    uiActionListener.dismissInputForm()
                },
                sheetState = sheetState
            ) {
                // Content of the bottom sheet
                // For example, a Column with some text
                Column {
                    Text("Bottom Sheet Content")
                    Button(onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                // showBottomSheet = false
                            }
                        }
                    }) {
                        Text("Hide")
                    }
                }
            }
        }

        CalendarEventOperationsState.RemoveTimedEventRequested -> {
            ModalBottomSheet(
                onDismissRequest = {
                    uiActionListener.dismissInputForm()
                },
                sheetState = sheetState
            ) {
                // Content of the bottom sheet
                // For example, a Column with some text
                Column {
                    Text("Bottom Sheet Content")
                    Button(onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                // showBottomSheet = false
                            }
                        }
                    }) {
                        Text("Hide")
                    }
                }
            }
        }
    }
}

private fun generateRandomColor(): Color {
    val red = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)
    return Color(red, green, blue)
}

@Preview(
    showBackground = true
)
@Composable
fun CalendarViewPreview() {
    val decimalSlotsStateController = remember {
        DecimalSlotsStateController(
            slotConfig = SlotConfig(slotScale = 2),
            eventsArrangement = EventsArrangement.MixedDirections(EventWidthType.FixedSizeFillLastEvent)
        ).apply {
            // Prepare the initial data
            Sample1(decimalSlotsStateController = this)
        }
    }
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            DecimalSlotsView(decimalSlotsStateController = decimalSlotsStateController) { event ->
                Box(
                    modifier = Modifier.fillMaxSize()
                        .padding(all = 2.dp)
                        .background(color = generateRandomColor())
                ) {
                    Text(
                        text = "${event.title}: ${event.startValue}-${event.endValue}",
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
