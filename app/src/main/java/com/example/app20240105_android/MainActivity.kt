package com.example.app20240105_android

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app20240105_android.components.RecordView
import com.example.app20240105_android.components.StudyLogView
import com.example.app20240105_android.components.TimerView
import com.example.app20240105_android.ui.theme.App20240105_AndroidTheme
import dagger.hilt.android.AndroidEntryPoint

data class BottomNavigationItem(
    val title: String,
    val destination: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val timerViewModel by viewModels<TimerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App20240105_AndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent(timerViewModel)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(timerViewModel: TimerViewModel) {
    // navigationを追加
    val navController = rememberNavController()

    val items = listOf(
        BottomNavigationItem(
            title = "タイマー",
            destination = "TimerView",
            selectedIcon = R.drawable.baseline_av_timer_24,
            unselectedIcon = R.drawable.baseline_av_timer_24,
            hasNews = false,
        ),
        BottomNavigationItem(
            title = "レポート",
            destination = "ReportView",
            selectedIcon = R.drawable.baseline_bar_chart_24,
            unselectedIcon = R.drawable.baseline_bar_chart_24,
            hasNews = false,
            badgeCount = 45
        ),
        BottomNavigationItem(
            title = "アカウント",
            destination = "AccountView",
            selectedIcon = R.drawable.baseline_account_circle_24,
            unselectedIcon = R.drawable.baseline_account_circle_24,
            hasNews = true,
        ),
    )
    // 画面遷移の状態を扱う変数を追加
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                            navController.navigate(item.destination)
                        },
                        label = { Text(text = item.title) },
                        alwaysShowLabel = true,
                        icon = {
                            Icon(
                                painterResource(
                                    if (index == selectedItemIndex) {
                                        item.selectedIcon
                                    } else item.unselectedIcon
                                ),
                                contentDescription = item.title
                            )
                        }
                    )
                }
            }
        }
    ) {
        // ここに画面下に表示されるBottomNavigationの画面遷移を記述
            padding ->
        NavHost(
            navController = navController,
            startDestination = items[selectedItemIndex].destination
        ) {
            composable("TimerView") {
                TimerView(navController)
            }
            composable("ReportView") {
                EmailPage("Chat", Modifier.padding(padding))
            }
            composable("AccountView") {
                SettingPage("Settings", Modifier.padding(padding))
            }
            composable("RecordView") {
                RecordView(navController)
            }
            composable("StudyLogView") {
                StudyLogView()
            }
        }

    }
}


@Composable
fun Label(icon: ImageVector, text: String, color: Color = MaterialTheme.colorScheme.onBackground) {
    Column() {
        Icon(
            imageVector = icon,
            contentDescription = text
        )
        Text(text = text, color = color, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun EmailPage(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun SettingPage(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}