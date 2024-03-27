package com.example.app20240105_android.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app20240105_android.viewModel.MainViewModel
import com.example.app20240105_android.R
import com.example.app20240105_android.models.Subject
import com.example.app20240105_android.viewModel.SubjectViewModel

@Composable
fun AccordionList(
    title: String,
    items: List<Subject>?,
    subjectViewModel: SubjectViewModel = hiltViewModel(), // 動いてる
    mainViewModel: MainViewModel = hiltViewModel()
) {
    var expanded by remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(if (expanded) 90f else 0f, label = "")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
            ) {
                Box(
                    modifier = Modifier
                        .graphicsLayer { rotationZ = rotationAngle }
                ) {
                    Icon(
                        painterResource(R.drawable.baseline_keyboard_arrow_right_24),
                        contentDescription = "arrow"
                    )
                    Spacer(modifier = Modifier.padding(end = 8.dp),)
                }
                Text(
                    text = title,
//                    modifier = Modifier.size(30.dp)
                )
            }

            if (expanded) {

                Row {
                    Spacer(modifier = Modifier.padding(start = 40.dp))
                    Column {
                        items?.forEach { item ->

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    // FIXME!!
                                    text = item.subjectName,
                                )
                                Row {
                                    Icon(
                                        painterResource(R.drawable.baseline_edit_24),
                                        contentDescription = "edit",
                                        Modifier.clickable {
                                            // TODO
                                        }
                                    )
                                    Icon(
                                        painterResource(R.drawable.baseline_delete_24),
                                        contentDescription = "delete",
                                        Modifier.clickable {
                                            subjectViewModel.deleteSubject(item)
                                        }
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                        }

                        // 追加ボタン
                        Icon(
                            painterResource(R.drawable.baseline_add_24),
                            contentDescription = "",
                            modifier = Modifier
                                .clickable{ mainViewModel.isShowDialog = true }
                        )

                        if (mainViewModel.isShowDialog) {
                            RegisterModal(mainViewModel)
                        }
                    }
                }
            }
        }
    }
}

//@Composable
//fun ExpandableListSample() {
//    val items = listOf("TOEIC900点", "ベース練習", "統計2級")
//    Column {
//        AccordionList(title = "科目", items = items)
//    }
//}