package com.example.w06

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.w06.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import java.util.UUID
import kotlin.math.roundToInt
import kotlin.random.Random

class BubbleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                BubbleScreen()
            }
        }
    }
}

data class Bubble(
    val id: UUID = UUID.randomUUID(),
    val x: Float,
    val y: Float,
    val color: Color,
    val visible: Boolean = false
)

fun randomBubble(maxWidth: Int, maxHeight: Int): Bubble {
    val colors = listOf(Color.Red, Color.Green, Color.Cyan, Color.Yellow)
    return Bubble(
        x = Random.nextInt(0, maxWidth).toFloat(),
        y = Random.nextInt(0, maxHeight).toFloat(),
        color = colors.random(),
        visible = false
    )
}

@Composable
fun BubbleScreen() {
    val bubbles = remember { mutableStateListOf<Bubble>() }
    var boxSize by remember { mutableStateOf(IntSize(0, 0)) }

    var score by remember { mutableStateOf(0) } // 점수
    var timeLeft by remember { mutableStateOf(30) } // 30초 제한 게임

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { coordinates -> boxSize = coordinates.size }
    ) {
        LaunchedEffect(Unit) {
            while (timeLeft > 0) {
                delay(1000L)
                timeLeft--
            }
        }
        LaunchedEffect(boxSize) {
            if (boxSize.width == 0 || boxSize.height == 0) return@LaunchedEffect

            if (bubbles.isEmpty()) {
                repeat(3) {
                    bubbles.add(randomBubble(boxSize.width, boxSize.height).copy(visible = true))
                }
                delay(2500L)
            }

            while (true) {
                if (timeLeft <= 0) break

                bubbles.replaceAll { it.copy(visible = false) }
                delay(500L)
                bubbles.replaceAll { randomBubble(boxSize.width, boxSize.height) }
                delay(100L)
                bubbles.replaceAll { it.copy(visible = true) }
                delay(2400L)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Score: $score", style = MaterialTheme.typography.titleLarge)
            Text(text = "Time: $timeLeft s", style = MaterialTheme.typography.titleLarge)
        }

        bubbles.forEach { bubble ->
            key(bubble.id) {
                BubbleComposable(bubble = bubble) {
                    if (timeLeft > 0) {
                        score++                   // 점수 증가
                        bubbles.remove(bubble)    // 클릭 시 사라지도록
                    }
                }
            }
        }

        if (timeLeft <= 0) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text(
                    text = "Game Over\nFinal Score: $score",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

@Composable
fun BubbleComposable(bubble: Bubble, onClick: () -> Unit) {
    val alpha by animateFloatAsState(
        targetValue = if (bubble.visible) 1f else 0f,
        animationSpec = tween(durationMillis = 500)
    )
    Box(
        modifier = Modifier
            .offset { IntOffset(bubble.x.roundToInt(), bubble.y.roundToInt()) }
            .size(50.dp)
            .background(bubble.color.copy(alpha = alpha), CircleShape)
            .clickable { onClick() }
    )
}