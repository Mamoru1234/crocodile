package com.mamoru.crocodile.screen.game.next_word_screen

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.abs

const val DELTA = 100

enum class SwipeDirection {
    UP,
    DOWN,
    NONE,
}

fun detectSwipeDirection(offset: Float): SwipeDirection {
    if (abs(offset) < DELTA) {
        return SwipeDirection.NONE
    }
    if (offset < 0) {
        return SwipeDirection.UP
    }
    return SwipeDirection.DOWN
}

@Composable
fun SwipingCard(
    onSwiped: (direction: SwipeDirection) -> Unit,
    onSwiping: (direction: SwipeDirection) -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    val offset = remember { mutableFloatStateOf(0f) }
    val currentDirection = remember { mutableStateOf(SwipeDirection.NONE) }
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.6f).pointerInput(Unit) {
            detectVerticalDragGestures(
                onVerticalDrag = { change, dragAmount ->
                    change.consume()
                    offset.floatValue += dragAmount
                    val newDirection = detectSwipeDirection(offset.floatValue)
                    if (newDirection != currentDirection.value) {
                        currentDirection.value = newDirection
                        onSwiping(currentDirection.value)
                    }
                },
                onDragEnd = {
                    onSwiped(currentDirection.value)
                    currentDirection.value = SwipeDirection.NONE
                    offset.floatValue = 0f
                }
            )
        },
        content = content,
    )
}