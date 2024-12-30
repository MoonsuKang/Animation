package com.kms.Animation.scratch.utils

import androidx.compose.ui.graphics.Path

/**
 * Calculate the percentage of the path that has been dragged.
 *
 * @param path The path to calculate the drag percentage of.
 * @param canvasWidth The width of the canvas.
 * @param canvasHeight The height of the canvas.
 * @return The percentage of the path that has been dragged.
 */
fun calculateDragPercent(path: Path, canvasWidth: Int, canvasHeight: Int): Float {
    val totalArea = canvasWidth * canvasHeight
    val pathArea = approximatePathArea(path, canvasWidth, canvasHeight)
    return (pathArea / totalArea.toFloat()) * 100
}
