package com.mamoru.crocodile.screen.game.next_word_screen

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun SwipingCard(
    onUpSwipe: () -> Unit,
    onDownSwipe: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    val offset = remember { mutableFloatStateOf(0f) }
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.6f).pointerInput(Unit) {
            detectVerticalDragGestures(
                onVerticalDrag = { change, dragAmount ->
                    change.consume()
                    offset.floatValue += dragAmount
                },
                onDragEnd = {
                    if (offset.floatValue < 0) {
                        onUpSwipe()
                    } else {
                        onDownSwipe()
                    }
                    offset.floatValue = 0f
                }
            )
        },
        content = content,
    )
}