package com.kms.Animation.scratch

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp


/**
 * A composable that displays a scratch card with an overlay image.
 *
 * @param overlayImage The image to overlay on top of the scratch card.
 * @param currentPath The current path of the user's scratch.
 * @param onDrag The callback to be invoked when the user drags on the scratch card.
 * @param onRelease The callback to be invoked when the user releases the scratch card.
 * @param content The content to be displayed on the scratch card.
 */
@Composable
fun ScratchCanvas(
    overlayImage: ImageBitmap,
    currentPath: Path,
    onDrag: (Offset, Int, Int) -> Unit,
    onRelease: () -> Unit,
    content: @Composable () -> Unit
) {
    var canvasSize by remember { mutableStateOf(IntSize(0, 0)) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.5f)
            .clip(RoundedCornerShape(12.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            content()
        }

        Canvas(
            modifier = Modifier
                .matchParentSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            onRelease()
                        },
                        onDrag = { change, _ ->
                            onDrag(change.position, canvasSize.width, canvasSize.height)
                        }
                    )
                }
                .onSizeChanged { size ->
                    canvasSize = size
                }
        ) {
            clipPath(path = currentPath, clipOp = ClipOp.Difference) {
                drawImage(overlayImage, dstSize = canvasSize)
            }
        }
    }
}
