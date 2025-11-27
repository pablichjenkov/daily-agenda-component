package com.macaosoftware.ui.dailyagenda.marker

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
internal fun CurrentTimeMarkerView(
    modifier: Modifier = Modifier,
    currentTimeMarkerStateController: CurrentTimeMarkerStateController
) {

    val state = currentTimeMarkerStateController.state

    Box(modifier = modifier
        .offset(y = state.value.offsetY)
        .height(height = 1.dp)
        .fillMaxWidth()
        .background(Color.Red)
    )
}