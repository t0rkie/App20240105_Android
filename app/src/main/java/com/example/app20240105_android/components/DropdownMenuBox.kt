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
import com.example.app20240105_android.viewModel.TimerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuBox(
    timerViewModel: TimerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val subjectViewModel = hiltViewModel<SubjectViewModel>()
    LaunchedEffect(Unit) { subjectViewModel.refreshSubjects() }
    val subjects by subjectViewModel.subjects.observeAsState()

    var expanded by remember { mutableStateOf(false) }

    // Stateの変更を監視してUIを再構築
    var selectedSubject by timerViewModel.selectedSubject

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
                    value =  if (selectedSubject.subjectName.isEmpty()) { "選択してください" } else { selectedSubject.subjectName },
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
                            timerViewModel.selectedSubject =  mutableStateOf(item) // FIXME!! 登録できてない
                            expanded = false
//                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}