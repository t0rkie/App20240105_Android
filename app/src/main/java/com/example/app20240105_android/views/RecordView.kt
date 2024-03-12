package com.example.app20240105_android.views

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.app20240105_android.models.MainViewModel
import com.example.app20240105_android.R
import com.example.app20240105_android.StudyLog
import com.example.app20240105_android.models.TimerViewModel
import com.example.app20240105_android.components.DropdownMenuBox
import com.example.app20240105_android.components.RecordItemRow
import com.example.app20240105_android.components.RegisterModal
import com.example.app20240105_android.models.StudyLogViewModel

// Subjectデータクラスの定義
data class Subject(
  val id: Int,
  val subjectName: String
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordView(
  timerViewModel: TimerViewModel,
  mainViewModel: MainViewModel,
  navController: NavController,
) {
//    val timerViewModel: TimerViewModel = hiltViewModel()
//    val mainViewModel: MainViewModel = hiltViewModel()
  val studyLogViewModel: StudyLogViewModel = hiltViewModel()

  // 状態変数
  var memo by remember { mutableStateOf("") }

  Scaffold(
    topBar = {
      CenterAlignedTopAppBar(
        title = { Text(text = "記録する") },
        navigationIcon = {
          if (navController.previousBackStackEntry != null) {
            IconButton(onClick = { navController.navigateUp() }) {
              Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back"
              )
            }
          } else { null }
        }
      )
    },
  ) {
      padding ->

    Column(
      horizontalAlignment = Alignment.Start, // 横方向
      modifier = Modifier
        .fillMaxWidth()
        .padding(
          top = padding.calculateTopPadding(),
          bottom = padding.calculateBottomPadding()
        )
    ) {
      // 勉強時間
      RecordItemRow(
        icon = R.drawable.baseline_av_timer_24,
        title = "勉強時間"
      ) {
        Text(
          text = timerViewModel.timeFormatted,
          modifier = Modifier.padding(end = 15.dp),
          color = Color(0xFF333333)
        )
      }

      // 科目選択セクション
      RecordItemRow(
        icon = R.drawable.baseline_edit_note_24,
        title = "科目"
      ) {
        DropdownMenuBox()
      }

      Row(
        modifier = Modifier
          .padding(start = 10.dp, end = 10.dp)
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
      ) {
        TextButton(onClick = { mainViewModel.isShowDialog = true }) {
          Text(text = "科目を追加")
        }
      }

      if (mainViewModel.isShowDialog) {
        RegisterModal()
      }


      // メモセクション
      Column(
        modifier = Modifier
          .padding(start = 10.dp, end = 10.dp, top = 10.dp)
          .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            painterResource(R.drawable.baseline_edit_note_24),
            contentDescription = "勉強時間"
          )
          Text(
            modifier = Modifier.padding(start = 10.dp),
            text = "メモ",
            color = Color(0xFF333333)
          )
        }
        BasicTextField(
          value = memo,
          onValueChange = { memo = it },
          modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
          ,
//                        .background(Color.Gray),
          decorationBox = { innerTextField ->
            Box(
              Modifier
                .padding(10.dp)
                .clip(RectangleShape)
                .background(LightGray)
            ) {
              innerTextField()
            }
          }
        )
      }


      Spacer(modifier = Modifier.height(200.dp))

      val context = LocalContext.current

      Row(
        modifier = Modifier
          .padding(start = 10.dp, end = 10.dp)
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
      ) {
        // 記録ボタン
        Button(onClick = {
          // TODO: 入力された値を登録するよう修正
          val log = StudyLog()
          log.studyTimeStr = timerViewModel.timeFormatted

          studyLogViewModel.addLog(log)

          mainViewModel.selectedItemIndex = 1 // FIXME: StudyLogViewのタブ番号
          navController.navigate("StudyLogView")
          Toast.makeText(context, "追加！", Toast.LENGTH_SHORT).show()
        }) {
          Text("記録する")
        }
      }
    }
  }
}
