package com.example.w01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClickGameWithTimer()
        }
    }
}

@Composable
fun ClickGameWithTimer() {
    var score by remember { mutableStateOf(0) }
    var gameStarted by remember { mutableStateOf(false) }
    var showResult by remember { mutableStateOf(false) }
    var remainingTime by remember { mutableStateOf(10) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!gameStarted && !showResult) {
            Button(onClick = {
                score = 0
                remainingTime = 10
                gameStarted = true
                showResult = false
                scope.launch {
                    while (remainingTime > 0) {
                        delay(1000)
                        remainingTime--
                    }
                    gameStarted = false
                    showResult = true
                }
            }) {
                Text("게임 시작")
            }
        }

        if (gameStarted) {
            Text("클릭 버튼을 누르면 점수가 오릅니다", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
            Text("남은 시간: $remainingTime 초", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text("점수: $score", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { score++ }) {
                Text("클릭!")
            }
        }

        if (showResult) {
            Spacer(modifier = Modifier.height(32.dp))
            Text("10초 동안 점수: $score", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                score = 0
                remainingTime = 10
                showResult = false
            }) {
                Text("다시하기")
            }
        }
    }
}