package com.kms.Animation.catchs

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CatchRoute() {
    var isCentered by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("초기화") }
    var barWidthDp by remember { mutableStateOf(0.dp) }

    val targetPosition = 0.5f
    val tolerance = 0.05f

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val position by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val onStop: () -> Unit = {
        isCentered = position in (targetPosition - tolerance)..(targetPosition + tolerance)
        message = if (isCentered) "성공" else "실패"
    }

    val onBarMeasured: (Dp) -> Unit = { width ->
        barWidthDp = width
    }

    CatchScreen(
        position = position,
        barWidthDp = barWidthDp,
        message = message,
        onStop = onStop,
        onBarMeasured = onBarMeasured
    )
}

@Composable
fun CatchScreen(
    position: Float,
    barWidthDp: Dp,
    message: String,
    onStop: () -> Unit,
    onBarMeasured: (Dp) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                MovingBar(
                    position = position,
                    barWidthDp = barWidthDp,
                    onBarMeasured = onBarMeasured
                )

                Button(
                    onClick = onStop,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(text = "정지")
                }
            }
        }
    }
}

@Composable
@Preview
fun CatchScreenPreview() {
    CatchRoute()
}