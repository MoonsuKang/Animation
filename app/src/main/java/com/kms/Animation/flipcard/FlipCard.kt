package com.kms.Animation.flipcard

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FlipCard(
    isFlipped: Boolean,
    onFlip: () -> Unit
) {
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = spring(stiffness = Spring.StiffnessMedium)
    )

    Box(
        modifier = Modifier
            .size(width = 200.dp, height = 300.dp)
            .graphicsLayer {
                cameraDistance = 12f * density
                rotationY = rotation
                scaleX = if (rotation > 90f) -1f else 1f
            }
            .clickable { onFlip() },
        contentAlignment = Alignment.Center
    ) {
        if (rotation <= 90f) {
            CardFace(text = "앞면", isFlipped = false) // 앞면
        } else {
            CardFace(text = "뒷면", isFlipped = true) // 뒷면
        }
    }
}


@Composable
@Preview
fun FlipCardPreview() {
    FlipCard(isFlipped = false, onFlip = {})
}