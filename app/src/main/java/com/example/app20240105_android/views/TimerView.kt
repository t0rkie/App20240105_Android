package com.example.app20240105_android.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.app20240105_android.R
import com.example.app20240105_android.models.TimerViewModel
import com.example.app20240105_android.components.IconButton


@Composable
fun TimerView(
    timerViewModel: TimerViewModel,
    navController: NavController
) {

    // TODO: hiltでViewModelを共通化
//    val timerViewModel: TimerViewModel = hiltViewModel()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally, // 横方向
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(200.dp))
        Text(
            text = timerViewModel.timeFormatted,
            fontSize = 70.sp
        )
        Spacer(modifier = Modifier.height(50.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            IconButton(
                icon = R.drawable.baseline_restore_24,
                text = "リセット",
                onClickFunction = { timerViewModel.resetTimer() }
            )

            IconButton(
                icon = R.drawable.baseline_play_arrow_24,
                text = "スタート",
                onClickFunction = { timerViewModel.startTimer() }
            )

            IconButton(
                icon = R.drawable.baseline_stop_24,
                text = "ストップ",
                onClickFunction = { timerViewModel.stopTimer() }
            )

        }
        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                timerViewModel.stopTimer()
                navController.navigate("RecordView")
            }
        ) {
            Text(text = "終了")
        }
    }
}