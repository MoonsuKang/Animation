package com.kms.Animation.scratch

import androidx.compose.ui.graphics.Path

data class DraggedPath(
    val path: Path = Path(),
    val brushSize: Float = 100f
)
