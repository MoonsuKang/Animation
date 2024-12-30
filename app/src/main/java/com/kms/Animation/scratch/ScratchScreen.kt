package com.kms.Animation.scratch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kms.Animation.scratch.utils.calculateDragPercent
import com.kms.Animation.scratch.utils.getDistanceTo

@Composable
fun ScratchRoute(
    overlayImage: ImageBitmap,
    fortuneText: String = "오늘의 총 운세 점수는\n90점 이야!"
) {
    val currentPath = remember { mutableStateOf(DraggedPath()) }
    val lastPoint = remember { mutableStateOf<Offset?>(null) }
    val dragPercent = remember { mutableStateOf(0f) }
    val isSuccess = remember { mutableStateOf(false) }

    ScratchScreen(
        overlayImage = overlayImage,
        currentPath = currentPath.value.path,
        onDrag = { offset, canvasWidth, canvasHeight ->
            val updatedPath = Path().apply {
                addPath(currentPath.value.path)

                lastPoint.value?.let { last ->
                    val distance = last.getDistanceTo(offset)
                    val steps = (distance / 5).toInt()
                    val dx = (offset.x - last.x) / steps
                    val dy = (offset.y - last.y) / steps

                    for (i in 1..steps) {
                        val x = last.x + dx * i
                        val y = last.y + dy * i
                        addOval(
                            Rect(
                                x - currentPath.value.brushSize / 2,
                                y - currentPath.value.brushSize / 2,
                                x + currentPath.value.brushSize / 2,
                                y + currentPath.value.brushSize / 2
                            )
                        )
                    }
                } ?: moveTo(offset.x, offset.y)
            }

            currentPath.value =
                DraggedPath(path = updatedPath, brushSize = currentPath.value.brushSize)
            lastPoint.value = offset

            val percent = calculateDragPercent(
                currentPath.value.path,
                canvasWidth = canvasWidth,
                canvasHeight = canvasHeight
            )
            dragPercent.value = percent
            isSuccess.value = percent >= 70f
        },
        onRelease = {
            lastPoint.value = null
        },
        content = {
            Text(
                text = fortuneText,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        },
        dragPercent = dragPercent.value,
        isSuccess = isSuccess.value
    )
}

@Composable
fun ScratchScreen(
    overlayImage: ImageBitmap,
    currentPath: Path,
    onDrag: (Offset, Int, Int) -> Unit,
    onRelease: () -> Unit,
    content: @Composable () -> Unit,
    dragPercent: Float,
    isSuccess: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScratchCanvas(
            overlayImage = overlayImage,
            currentPath = currentPath,
            onDrag = onDrag,
            onRelease = onRelease,
            content = content
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "드래그 영역: ${"%.1f".format(dragPercent)}%",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = if (isSuccess) "성공!" else "실패",
            style = MaterialTheme.typography.bodyLarge,
            color = if (isSuccess) Color.Green else Color.Red,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
