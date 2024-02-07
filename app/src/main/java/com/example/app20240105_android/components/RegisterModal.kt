package com.example.app20240105_android.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app20240105_android.MainViewModel
import com.example.app20240105_android.TimerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterModal(mainViewModel: MainViewModel = hiltViewModel()) {
    var textState by remember { mutableStateOf("") } // テキスト入力状態の保持

    Dialog(onDismissRequest = { mainViewModel.isShowDialog = false }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("データ登録")
                Spacer(modifier = Modifier.height(20.dp))
                // テキスト入力フィールド
                TextField(
                    value = textState,
                    onValueChange = { textState = it },
                    label = { Text("入力してください") }
                )
                Spacer(modifier = Modifier.height(20.dp))
                // 登録ボタン
                Button(onClick = {
                    // ここにデータ登録の処理を書く
                    println("登録: $textState") // 仮の処理
                    mainViewModel.isShowDialog = false // ダイアログを閉じる
                }) {
                    Text("登録")
                }
            }
        }
    }
}