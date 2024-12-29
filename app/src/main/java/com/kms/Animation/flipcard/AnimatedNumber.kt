package com.kms.Animation.flipcard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun AnimatedNumber(
    number: Int,
    onAnimationEnd: () -> Unit
) {
    val animationDuration = 1500

    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + slideInVertically(initialOffsetY = { -50 }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { -50 })
    ) {
        Text(
            text = "+$number",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color(0xFFFDFE96),
                fontSize = 32.sp
            )
        )
    }

    LaunchedEffect(number) {
        kotlinx.coroutines.delay(animationDuration.toLong())
        onAnimationEnd()
    }
}

@Composable
@Preview
fun AnimatedNumberPreview() {
    AnimatedNumber(number = 10, onAnimationEnd = {})
}
