package com.example.app20240105_android.components

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.app20240105_android.R
import com.example.app20240105_android.TimerViewModel

// Subjectデータクラスの定義
data class Subject(
    val id: Int,
    val subjectName: String
)

// subjectsリストの初期化
val subjects = listOf(
    Subject(id = 1, subjectName = "TOEIC勉強"),
    Subject(id = 2, subjectName = "ベース"),
    Subject(id = 3, subjectName = "統計2級"),
    Subject(id = 4, subjectName = "音楽理論"),
    Subject(id = 5, subjectName = "副業"),
    Subject(id = 6, subjectName = "アプリ開発"),
    Subject(id = 7, subjectName = "絵の勉強"),
    Subject(id = 8, subjectName = "読書")
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordView(
    navController: NavController,
    timerViewModel: TimerViewModel = hiltViewModel()
) {

    // 状態変数
    var selectedSubjectId by remember { mutableStateOf(0) }
    var showSheet by remember { mutableStateOf(false) }
    var newSubjectName by remember { mutableStateOf("") }
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
                TextButton(onClick = { showSheet = true }) {
                    Text(text = "科目を追加")
                }
            }

            DropdownMenu(
                expanded = showSheet,
                onDismissRequest = { showSheet = false },
            ) {
                subjects.forEach { subject ->
                    DropdownMenuItem(
                        text = { Text(subject.subjectName) },
                        onClick = {
                            selectedSubjectId = subject.id
                            showSheet = false
                        },
                    )
                }
            }
            RegisterModal()

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

            RecordItemRow(
                icon = R.drawable.baseline_text_snippet_24,
                title = "教材"
            ) {
                Text(
                    text = timerViewModel.timeFormatted,
                    modifier = Modifier.padding(end = 15.dp),
                    color = Color(0xFF333333)
                )
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

            Row(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                // 記録ボタン
                Button(onClick = { navController.navigate("StudyLogView") }) {
                    Text("記録する")
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuBox() {
    val context = LocalContext.current
    val subjects = arrayOf("TOEIC勉強", "ベース", "統計2級", "音楽理論", "アプリ開発")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(subjects[0]) }

    Box(
        modifier = Modifier
            .padding(start = 10.dp)
            .width(200.dp)
        ,
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                subjects.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
//                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterModal() {
    var textState by remember { mutableStateOf("") } // テキスト入力状態の保持
    val openDialog = remember { mutableStateOf(false) } // ダイアログ表示状態の保持

    // ダイアログを開くためのボタン
    Button(onClick = { openDialog.value = true }) {
        Text("登録モーダルを開く")
    }

    // ダイアログ表示
    if (openDialog.value) {
        Dialog(onDismissRequest = { openDialog.value = false }) {
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
                        openDialog.value = false // ダイアログを閉じる
                    }) {
                        Text("登録")
                    }
                }
            }
        }
    }
}