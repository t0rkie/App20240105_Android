package com.example.app20240105_android.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StudyLogView() {
    LazyColumn {
        items(10) { index ->
            StudyLogRow()
        }
    }
}

@Composable
fun StudyLogRow() {
    Column {
        Text(text = "2024年1月5日")
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Column {
                Text(text = "TOIEC900点")

                Row(
                    modifier = Modifier
                        .clickable { }
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

//                Spacer(modifier = Modifier.weight(1f))
                    Text(text = "1時間")

                    IconButton(onClick = { }) {
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
    StudyLogRow()
}