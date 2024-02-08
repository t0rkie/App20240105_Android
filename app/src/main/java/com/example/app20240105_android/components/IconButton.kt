package com.example.app20240105_android.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.app20240105_android.R

@Composable
fun IconButton(
    icon: Int,
    text: String,
    onClickFunction: () -> Unit
) {
    TextButton(onClick = { onClickFunction() }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painterResource(icon),
                contentDescription = "",
                modifier = Modifier.size(40.dp)
            )
            Text(text = text)
        }
    }
}