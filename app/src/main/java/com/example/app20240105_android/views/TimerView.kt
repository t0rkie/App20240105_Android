package com.example.app20240105_android.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.app20240105_android.TimerViewModel


@Composable
fun TimerView(
    navController: NavController,
    timerViewModel: TimerViewModel = hiltViewModel()
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally, // 横方向
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(200.dp))
        Text(
            text = timerViewModel.timeFormatted,
            fontSize = 60.sp
        )
        Spacer(modifier = Modifier.height(50.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            TextButton(onClick = { timerViewModel.startTimer() }) {
                Text(text = "勉強開始")
            }

            TextButton(onClick = { timerViewModel.stopTimer() }) {
                Text(text = "ストップ")
            }

            TextButton(onClick = { timerViewModel.resetTimer() }) {
                Text(text = "リセット")
            }
        }
        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                navController.navigate("RecordView")
            }
        ) {
            Text(text = "終了")
        }
    }
}