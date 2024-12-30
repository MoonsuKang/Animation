package com.kms.Animation.scratch.utils

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath

/**
 * Approximate the area of a path by drawing it on a bitmap and counting the non-transparent pixels.
 *
 * @param path The path to approximate the area of.
 * @param width The width of the bitmap.
 * @param height The height of the bitmap.
 * @return The approximate area of the path.
 */
fun approximatePathArea(path: Path, width: Int, height: Int): Int {
    val bitmap =
        android.graphics.Bitmap.createBitmap(width, height, android.graphics.Bitmap.Config.ALPHA_8)
    val canvas = android.graphics.Canvas(bitmap)
    val paint = android.graphics.Paint().apply { style = android.graphics.Paint.Style.FILL }
    canvas.drawPath(path.asAndroidPath(), paint)

    val pixels = IntArray(width * height)
    bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
    return pixels.count { it != 0 }
}
