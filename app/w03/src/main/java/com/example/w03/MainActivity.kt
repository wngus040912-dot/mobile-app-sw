package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.w03.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    KeypadScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun KeypadScreen(modifier: Modifier = Modifier) {
    var phoneNumber by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "+ 연락처 추가",
            color = Color.Green,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 20.dp)
        )
        Text(
            text = "L.J.H. Phone",
            color = Color.Black,
            fontSize = 15.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = phoneNumber,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 20.dp),
            textAlign = TextAlign.Center
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val keys = listOf(
                listOf("1", "2", "3"),
                listOf("4", "5", "6"),
                listOf("7", "8", "9"),
                listOf("*", "0", "#")
            )

            for (row in keys) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    for (key in row) {
                        KeyButton(
                            label = key,
                            onClick = {
                                if (phoneNumber.length < 15) {
                                    phoneNumber += key
                                }
                            }
                        )
                    }
                }
            }
        }

        // 하단 버튼 영역
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { /* TODO: 최근기록 기능 */ }
            ) {
                Text(text = "최근기록", fontSize = 14.sp, color = Color.Gray)
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { /* TODO: 전화 기능 */ }
            ) {
                Text(text = "통화", fontSize = 14.sp, color = Color.Gray)
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { /* TODO: 연락처 기능 */ }
            ) {
                Text(text = "연락처", fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun KeyButton(label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .padding(8.dp)
            .clickable { onClick() }
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun KeypadScreenPreview() {
    MyApplicationTheme {
        KeypadScreen()
    }
}