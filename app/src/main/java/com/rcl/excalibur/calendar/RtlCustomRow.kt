package com.rcl.excalibur.calendar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

@Composable
fun RtlCustomRow(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->

        // 1. Measure children
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        // 2. Calculate layout size (e.g., sum of widths for a horizontal row)
        var currentX = 0
        var maxHeight = 0
        placeables.forEach { placeable ->
            currentX += placeable.width
            if (placeable.height > maxHeight) {
                maxHeight = placeable.height
            }
        }

        // 3. Place children
        layout(width = currentX, height = maxHeight) {
            var xPosition = constraints.maxWidth
            placeables.forEach { placeable ->

                xPosition -= placeable.width
                placeable.placeRelative(x = xPosition, y = 0)
                //xPosition += placeable.width

            }
        }
    }
}
