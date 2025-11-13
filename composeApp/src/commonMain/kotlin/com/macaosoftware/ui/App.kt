package com.macaosoftware.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.macaosoftware.ui.dailyagenda.DailyAgendaView
import com.macaosoftware.ui.dailyagenda.DecimalSlotsStateController
import com.macaosoftware.ui.dailyagenda.EventWidthType
import com.macaosoftware.ui.dailyagenda.EventsArrangement
import com.macaosoftware.ui.dailyagenda.SlotConfig
import com.macaosoftware.ui.dailyagenda.TimeSlotsStateController
import com.macaosoftware.ui.dailyagenda.toLocalTimeEvent
import com.macaosoftware.ui.data.Sample0
import com.macaosoftware.ui.data.Sample1
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.random.Random

@Composable
@Preview
fun App() {
    MaterialTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = innerPadding)
            ) {
                val stateController = remember {
                    val slotConfig = SlotConfig(slotScale = 1, slotHeight = 102)
                    val timeSlotsStateController = TimeSlotsStateController(slotConfig = slotConfig)

                    // Prepare the initial data
                    Sample0(timeSlotsStateController = timeSlotsStateController)
                    timeSlotsStateController
                }
//                val stateController = remember {
//                    val slotConfig = SlotConfig(slotScale = 2)
//                    val decimalSlotsStateController =
//                        DecimalSlotsStateController(
//                            slotConfig = slotConfig,
//                            eventsArrangement = EventsArrangement.MixedDirections(EventWidthType.MaxVariableSize)
//                        )
//
//                    // Prepare the initial data
//                    Sample1(decimalSlotsStateController = decimalSlotsStateController)
//                    decimalSlotsStateController
//                }
                DailyAgendaView(
                    dailyAgendaState = stateController.state.value
                ) { event ->
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .padding(all = 2.dp)
                            .background(color = generateRandomColor())
                    ) {
                        if (stateController is TimeSlotsStateController) {
                            val localTimeEvent = event.toLocalTimeEvent()
                            Text(
                                text = "${event.title}: ${localTimeEvent.startTime}-${localTimeEvent.endTime}",
                                fontSize = 12.sp
                            )
                        } else {
                            Text(
                                text = "${event.title}: ${event.startValue}-${event.endValue}",
                                fontSize = 12.sp
                            )
                        }

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
    val stateController = remember {
        val slotConfig = SlotConfig(slotScale = 1, slotHeight = 72)
        val decimalSlotsStateController = DecimalSlotsStateController(slotConfig = slotConfig)

        // Prepare the initial data
        Sample1(decimalSlotsStateController = decimalSlotsStateController)

        decimalSlotsStateController
    }
    MaterialTheme {
        Box(modifier = Modifier.size(600.dp, 1000.dp)) {
            DailyAgendaView(
                dailyAgendaState = stateController.state.value
            ) { event ->
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(color = generateRandomColor())
                ) {
                    val localTimeEvent = event.toLocalTimeEvent()
                    Text(
                        text = "${event.title}: ${localTimeEvent.startTime}-${localTimeEvent.endTime}",
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
