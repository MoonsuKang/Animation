package com.kms.Animation.flipcard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CardFlipRoute() {
    var flipCount by remember { mutableStateOf(0) }
    var isFlipped by remember { mutableStateOf(false) }
    val numberList = remember { mutableStateListOf<Int>() }

    val onFlip: () -> Unit = {
        numberList.add(flipCount + 1)
        if (flipCount < 4) {
            flipCount += 1
        } else {
            flipCount = 0
            isFlipped = !isFlipped
        }
    }

    val onNumberAnimationEnd: (Int) -> Unit = { number ->
        numberList.remove(number)
    }

    val derivedNumberList by remember {
        derivedStateOf { numberList.toList() }
    }

    CardFlipScreen(
        flipCount = flipCount,
        isFlipped = isFlipped,
        numberList = derivedNumberList,
        onFlip = onFlip,
        onNumberAnimationEnd = onNumberAnimationEnd
    )
}

@Composable
fun CardFlipScreen(
    flipCount: Int,
    isFlipped: Boolean,
    numberList: List<Int>,
    onFlip: () -> Unit,
    onNumberAnimationEnd: (Int) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            CardAndText(
                flipCount = flipCount,
                isFlipped = isFlipped,
                onFlip = onFlip
            )

            AnimatedNumbers(
                numberList = numberList,
                onNumberAnimationEnd = onNumberAnimationEnd
            )
        }
    }
}

@Composable
fun CardAndText(
    flipCount: Int,
    isFlipped: Boolean,
    onFlip: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FlipCard(isFlipped = isFlipped, onFlip = onFlip)
        Text(
            text = "$flipCount / 5회",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun AnimatedNumbers(
    numberList: List<Int>,
    onNumberAnimationEnd: (Int) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        numberList.forEachIndexed { index, number ->
            key(number) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(
                            x = (100.dp + (index * 10).dp),
                            y = (-200).dp - (index * 30).dp
                        )

                ) {
                    AnimatedNumber(
                        number = number,
                        onAnimationEnd = { onNumberAnimationEnd(number) }
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CardFlipScreenPreview() {
    CardFlipRoute()
}
