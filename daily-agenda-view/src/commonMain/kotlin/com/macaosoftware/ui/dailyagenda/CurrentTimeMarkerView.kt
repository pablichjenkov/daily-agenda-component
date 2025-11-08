package com.macaosoftware.ui.dailyagenda

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CurrentTimeMarkerView(
    currentTimeMarkerStateController: CurrentTimeMarkerStateController
) {

    val state = currentTimeMarkerStateController.state

    Box(modifier = Modifier
        .offset(y = state.value.offsetY)
        .height(height = 1.dp)
        .fillMaxWidth()
        .background(Color.Red)
    )
}