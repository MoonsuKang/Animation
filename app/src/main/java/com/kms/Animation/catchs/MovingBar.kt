package com.kms.Animation.catchs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MovingBar(
    position: Float,
    barWidthDp: Dp,
    onBarMeasured: (Dp) -> Unit
) {
    val barWidthFraction = 0.8f
    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(barWidthFraction)
                .height(20.dp)
                .background(Color.Gray, shape = RoundedCornerShape(10.dp))
                .onGloballyPositioned { coordinates ->
                    val width = with(density) { coordinates.size.width.toDp() }
                    onBarMeasured(width)
                }
        )

        Box(
            modifier = Modifier
                .width(60.dp)
                .height(20.dp)
                .background(Color.LightGray)
        )

        Box(
            modifier = Modifier
                .offset(x = ((position - 0.5f) * barWidthDp.value).dp, y = 24.dp)
                .size(12.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val trianglePath = Path().apply {
                    moveTo(size.width / 2, 0f)
                    lineTo(0f, size.height)
                    lineTo(size.width, size.height)
                    close()
                }
                drawPath(
                    path = trianglePath,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
@Preview
fun MovingBarPreview() {
    var barWidthDp by remember { mutableStateOf(0.dp) }

    MovingBar(
        position = 0.5f,
        barWidthDp = barWidthDp,
        onBarMeasured = { width ->
            barWidthDp = width
        }
    )
}