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
import com.macaosoftware.ui.dailyagenda.DailyAgendaStateController
import com.macaosoftware.ui.dailyagenda.DailyAgendaView
import com.macaosoftware.ui.dailyagenda.DecimalSlotsGenerator
import com.macaosoftware.ui.dailyagenda.TimeLineSlotsGenerator
import com.macaosoftware.ui.data.DemoConfigurations
import com.macaosoftware.ui.data.Sample0
import com.macaosoftware.ui.data.Sample3
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
                    val slotsGenerator = TimeLineSlotsGenerator()
                    val demoConfigurations = DemoConfigurations(slotsGenerator)

                    DailyAgendaStateController(
                        slotsGenerator = slotsGenerator,
                        slotToEventMap = Sample3(slotsGenerator = slotsGenerator).slotToEventMap,
                        config = demoConfigurations.demoConfigLTR
                    )
                }
                DailyAgendaView(
                    dailyAgendaState = stateController.state.value
                ) { event ->
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .padding(2.dp)
                            .background(color = generateRandomColor())
                    ) {
                        Text(text = "${event.title}: ${event.startTime}-${event.endTime}")
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
        val slotsGenerator = DecimalSlotsGenerator()
        val demoConfigurations = DemoConfigurations(slotsGenerator)

        DailyAgendaStateController(
            slotsGenerator = slotsGenerator,
            slotToEventMap = Sample0(slotsGenerator = slotsGenerator).slotToEventMap,
            config = demoConfigurations.demoConfigLTR
        )
    }
    stateController.state.value?.let { dailyAgendaState ->
        MaterialTheme {
            Box(modifier = Modifier.size(600.dp, 1000.dp)) {
                DailyAgendaView(
                    dailyAgendaState = dailyAgendaState
                ) { event ->
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .background(color = generateRandomColor())
                    ) {
                        Text(text = "${event.title}: ${event.startTime}-${event.endTime}")
                    }
                }
            }
        }
    }
}
