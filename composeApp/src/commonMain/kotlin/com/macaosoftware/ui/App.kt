package com.macaosoftware.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.macaosoftware.ui.dailyagenda.Config
import com.macaosoftware.ui.dailyagenda.DailyAgendaStateController
import com.macaosoftware.ui.dailyagenda.DailyAgendaView
import com.macaosoftware.ui.data.Sample0
import com.macaosoftware.ui.data.Sample3
import com.macaosoftware.ui.data.Slots
import org.jetbrains.compose.ui.tooling.preview.Preview

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
                val dailyAgendaStateController = remember {
                    DailyAgendaStateController(
                        slots = Slots.slots,
                        slotToEventMap = Sample0(Slots.slots).slotToEventMap,
                        config = Slots.demoConfigMixedDirections
                    )
                }

                dailyAgendaStateController.state.value?.let { dailyAgendaState ->
                    DailyAgendaView(
                        dailyAgendaState = dailyAgendaState
                    )
                }

                LaunchedEffect(key1 = dailyAgendaStateController) {
                    dailyAgendaStateController.start()
                }
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun CalendarViewPreview() {
    val stateController = remember {
        DailyAgendaStateController(
            slots = Slots.slots,
            slotToEventMap = Sample0(Slots.slots).slotToEventMap,
            config = Slots.demoConfigLTR
        ).also {
            it.start()
        }
    }
    stateController.state.value?.let { dailyAgendaState ->
        MaterialTheme {
            Box(modifier = Modifier.size(600.dp, 1000.dp)) {
                DailyAgendaView(
                    dailyAgendaState = dailyAgendaState
                )
            }
        }
    }
}
