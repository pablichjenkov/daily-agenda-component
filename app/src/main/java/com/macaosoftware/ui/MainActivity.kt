package com.macaosoftware.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.macaosoftware.ui.data.Sample3
import com.macaosoftware.ui.data.Slots
import com.macaosoftware.ui.theme.CalendarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalendarTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues = innerPadding)
                    ) {
                        val dailyAgendaState = remember {
                            DailyAgendaStateController(
                                slots = Slots.slots,
                                slotToEventMap = Sample3(Slots.slots).slotToEventMap,
                                config = Config.MixedDirections(eventWidthType = EventWidthType.FixedSizeFillLastEvent)
                            ).computeNextState()
                        }
                        DailyAgendaView(
                            dailyAgendaState = dailyAgendaState
                        )
                    }
                }
            }
        }
    }
}
