package com.kms.Animation.scratch.utils

import androidx.compose.ui.geometry.Offset

/**
 * Euclidean algorithm
 * Calculate the distance between two [Offset]s.
 *
 * @param other The other [Offset] to calculate the distance to.
 * @return The distance between this [Offset] and [other].
 */
fun Offset.getDistanceTo(other: Offset): Float {
    return kotlin.math.sqrt(
        (other.x - this.x) * (other.x - this.x) + (other.y - this.y) * (other.y - this.y)
    )
}
