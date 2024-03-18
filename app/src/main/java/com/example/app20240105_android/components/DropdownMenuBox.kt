package com.example.app20240105_android.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app20240105_android.viewModel.SubjectViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuBox() {
    val context = LocalContext.current
//    val subjects = arrayOf("TOEIC勉強", "ベース", "統計2級", "音楽理論", "アプリ開発")
    val subjectViewModel = hiltViewModel<SubjectViewModel>()
    LaunchedEffect(Unit) { subjectViewModel.refreshSubjects() }
    val subjects by subjectViewModel.subjects.observeAsState()


    var expanded by remember { mutableStateOf(false) }
//    var selectedText by remember { mutableStateOf(subjects?.get(0)?.subjectName ?: "") }

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
            Box {
                TextField(
                    value = "選択してください",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor()
                )
            }

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                subjects?.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.subjectName) },
                        onClick = {
//                            selectedText = item.subjectName
                            expanded = false
//                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}