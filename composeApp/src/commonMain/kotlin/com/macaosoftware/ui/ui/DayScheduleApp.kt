package com.macaosoftware.ui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.macaosoftware.ui.dailyagenda.decimalslots.DecimalSlotConfig
import com.macaosoftware.ui.dailyagenda.decimalslots.DecimalSlotsStateController
import com.macaosoftware.ui.dailyagenda.decimalslots.DecimalSlotsView
import com.macaosoftware.ui.dailyagenda.decimalslots.EventWidthType
import com.macaosoftware.ui.dailyagenda.decimalslots.EventsArrangement
import com.macaosoftware.ui.dailyagenda.epgslots.EpgChannel
import com.macaosoftware.ui.dailyagenda.epgslots.EpgChannelSlotConfig
import com.macaosoftware.ui.dailyagenda.epgslots.EpgSlotsStateController
import com.macaosoftware.ui.dailyagenda.epgslots.EpgSlotsView
import com.macaosoftware.ui.dailyagenda.timeslots.LocalTimeEvent
import com.macaosoftware.ui.dailyagenda.timeslots.TimeSlotConfig
import com.macaosoftware.ui.dailyagenda.timeslots.TimeSlotsStateController
import com.macaosoftware.ui.dailyagenda.timeslots.TimeSlotsView
import com.macaosoftware.ui.data.Constants
import com.macaosoftware.ui.data.DecimalSegmentDataSample
import com.macaosoftware.ui.ui.model.AllDayEvent
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.random.Random
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

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
                when (viewModel.slotsViewType) {
                    SlotsViewType.Decimal -> {
                        DecimalSlotExample(
                            decimalSlotsStateController = viewModel.decimalSlotsStateController,
                            uiActionListener = viewModel.uiActionListener
                        )
                    }

                    SlotsViewType.Timeline -> {
                        TimeSlotExample(
                            allDayEvents = viewModel.allDayEvents,
                            timeSlotsStateController = viewModel.timeSlotsStateController,
                            uiActionListener = viewModel.uiActionListener
                        )
                    }

                    SlotsViewType.Epg -> {
                        EpgSlotExample(
                            uiActionListener = viewModel.uiActionListener
                        )
                    }
                }
                CalendarEventActionsView(
                    slotsViewType = viewModel.slotsViewType,
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
    allDayEvents: List<AllDayEvent>,
    timeSlotsStateController: TimeSlotsStateController,
    uiActionListener: DayScheduleAppViewModel.UiActionListener
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.height(56.dp).fillMaxWidth().background(Color.LightGray)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "All Day Events = ${allDayEvents.size}"
            )
        }
        TimeSlotsView(
            timeSlotsStateController = timeSlotsStateController
        ) { localTimeEvent ->
            Box(
                modifier = Modifier.fillMaxSize()
                    .padding(all = 2.dp)
                    .background(color = generateRandomColor())
                    .combinedClickable(
                        onClick = {
                            uiActionListener.onTimeEventClicked(localTimeEvent)
                        },
                        onDoubleClick = {
                            uiActionListener.onTimeEventDoubleClicked(localTimeEvent)
                        },
                        onLongClick = {
                            uiActionListener.onTimeEventLongClicked(localTimeEvent)
                        }
                    )
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
    decimalSlotsStateController: DecimalSlotsStateController,
    uiActionListener: DayScheduleAppViewModel.UiActionListener
) {
    Box(modifier = Modifier.fillMaxSize()) {
        DecimalSlotsView(
            decimalSlotsStateController = decimalSlotsStateController
        ) { decimalEvent ->
            Box(
                modifier = Modifier.fillMaxSize()
                    .padding(all = 2.dp)
                    .background(color = generateRandomColor())
                    .combinedClickable(
                        onClick = {
                            uiActionListener.onDecimalEventClicked(decimalEvent)
                        },
                        onDoubleClick = {
                            uiActionListener.onDecimalEventDoubleClicked(decimalEvent)
                        },
                        onLongClick = {
                            uiActionListener.onDecimalEventLongClicked(decimalEvent)
                        }
                    )
            ) {
                Text(
                    text = "${decimalEvent.title}: ${decimalEvent.startValue}-${decimalEvent.endValue}",
                    fontSize = 12.sp
                )
            }
        }

    }
}

@OptIn(ExperimentalUuidApi::class)
@Composable
private fun EpgSlotExample(
    uiActionListener: DayScheduleAppViewModel.UiActionListener
) {

    val epgSlotsStateController = remember {
        EpgSlotsStateController(
            EpgChannelSlotConfig(
                timeSlotConfig = TimeSlotConfig(
                    startSlotTime = LocalTime(6, 0),
                    endSlotTime = LocalTime(23, 59)
                )
            )
        ).apply {
            epgSlotsDataUpdater.addChannel(
                EpgChannel(
                    name = "Ch1",
                    events = listOf(
                        LocalTimeEvent(
                            uuid = Uuid.random(),
                            title = "Ev1",
                            description = Constants.EmptyDescription,
                            startTime = LocalTime(9, 0),
                            endTime = LocalTime(10, 0)
                        ),
                        LocalTimeEvent(
                            uuid = Uuid.random(),
                            title = "Ev2",
                            description = Constants.EmptyDescription,
                            startTime = LocalTime(10, 0),
                            endTime = LocalTime(11, 30)
                        )
                    )
                )
            )
            epgSlotsDataUpdater.addChannel(
                EpgChannel(
                    name = "Ch2",
                    events = listOf(
                        LocalTimeEvent(
                            uuid = Uuid.random(),
                            title = "Ev3",
                            description = Constants.EmptyDescription,
                            startTime = LocalTime(9, 30),
                            endTime = LocalTime(10, 15)
                        ),
                        LocalTimeEvent(
                            uuid = Uuid.random(),
                            title = "Ev4",
                            description = Constants.EmptyDescription,
                            startTime = LocalTime(10, 30),
                            endTime = LocalTime(11, 0)
                        )
                    )
                )
            )
            epgSlotsDataUpdater.addChannel(
                EpgChannel(
                    name = "Ch3",
                    events = listOf(
                        LocalTimeEvent(
                            uuid = Uuid.random(),
                            title = "Ev5",
                            description = Constants.EmptyDescription,
                            startTime = LocalTime(10, 0),
                            endTime = LocalTime(11, 0)
                        ),
                        LocalTimeEvent(
                            uuid = Uuid.random(),
                            title = "Ev6",
                            description = Constants.EmptyDescription,
                            startTime = LocalTime(12, 0),
                            endTime = LocalTime(13, 30)
                        )
                    )
                )
            )
            epgSlotsDataUpdater.commit()
        }
    }

    EpgSlotsView(
        epgSlotsStateController = epgSlotsStateController
    ) { localTimeEvent ->
        Box(
            modifier = Modifier.fillMaxSize().padding(2.dp)
                .background(generateRandomColor())
                .combinedClickable(
                    onClick = {
                        uiActionListener.onEpgEventClicked(localTimeEvent)
                    },
                    onDoubleClick = {
                        uiActionListener.onEpgEventDoubleClicked(localTimeEvent)
                    },
                    onLongClick = {
                        uiActionListener.onEpgEventLongClicked(localTimeEvent)
                    }
                )
        ) {
            Text(
                text = "${localTimeEvent.title}: ${localTimeEvent.startTime}-${localTimeEvent.endTime}",
                fontSize = 12.sp
            )
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
            decimalSlotConfig = DecimalSlotConfig(slotScale = 2),
            eventsArrangement = EventsArrangement.MixedDirections(EventWidthType.FixedSizeFillLastEvent)
        ).apply {
            // Prepare the initial data
            DecimalSegmentDataSample(decimalSlotsStateController = this)
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
