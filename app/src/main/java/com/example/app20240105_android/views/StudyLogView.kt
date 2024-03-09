package com.example.app20240105_android.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app20240105_android.StudyLog
import com.example.app20240105_android.StudyLogRepository
import com.example.app20240105_android.models.StudyLogViewModel

@Composable
fun StudyLogView() {
    val studyLogViewModel = hiltViewModel<StudyLogViewModel>()
    LaunchedEffect(Unit) { studyLogViewModel.refreshLogs() }
    val logs by studyLogViewModel.logs.observeAsState()

    logs?.let { logList ->
        LazyColumn{
            items(logList) {
                StudyLogRow(it)
            }
        }
    }
}

@Composable
fun StudyLogRow(log: StudyLog) {
    val studyLogViewModel = hiltViewModel<StudyLogViewModel>()
    val context = LocalContext.current
    Column {
        Text(text = "2024年1月5日")
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Column {
                Text(text = log.subjectId.toString()) // TODO: 科目名を表示

                Row(
                    modifier = Modifier
                        .clickable { }
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

//                Spacer(modifier = Modifier.weight(1f))
                    Text(text = log.studyTimeStr)

                    IconButton(onClick = {
                        studyLogViewModel.deleteLog(log)
                        Toast.makeText(context, "削除！", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "delete")
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun StudyLogRowPreview() {
    StudyLogRow(StudyLog())
}