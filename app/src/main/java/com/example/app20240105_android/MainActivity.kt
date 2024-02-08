package com.example.app20240105_android

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app20240105_android.views.RecordView
import com.example.app20240105_android.views.StudyLogView
import com.example.app20240105_android.views.TimerView
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
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App20240105_AndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent(timerViewModel, mainViewModel)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(timerViewModel: TimerViewModel, mainViewModel: MainViewModel) {
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
            title = "記録",
            destination = "StudyLogView",
            selectedIcon = R.drawable.baseline_text_snippet_24,
            unselectedIcon = R.drawable.baseline_text_snippet_24,
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
            title = "ホーム",
            destination = "HomeView",
            selectedIcon = R.drawable.baseline_home_24,
            unselectedIcon = R.drawable.baseline_home_24,
            hasNews = true,
        ),
    )
    // 画面遷移の状態を扱う変数を追加
//    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = mainViewModel.selectedItemIndex == index,
                        onClick = {
                            mainViewModel.selectedItemIndex = index
                            navController.navigate(item.destination)
                        },
                        label = { Text(text = item.title) },
                        alwaysShowLabel = true,
                        icon = {
                            Icon(
                                painterResource(
                                    if (index == mainViewModel.selectedItemIndex) {
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
            startDestination = items[mainViewModel.selectedItemIndex].destination
        ) {
            composable("TimerView") {
                TimerView(navController)
            }
            composable("ReportView") {
                ReportView("Chat", Modifier.padding(padding))
            }
            composable("HomeView") {
                HomeView("Settings", Modifier.padding(padding))
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
fun ReportView(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(name: String, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "ホーム") },
            )
        },
    ) {
            padding ->
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                )
        ) {
            ExpandableListSample()
        }
    }
}

@Composable
fun ExpandableCard(title: String, items: List<String>) {
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
                        items.forEach { item ->

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = item,
                                )
                                Icon(
                                    painterResource(R.drawable.baseline_keyboard_arrow_right_24),
                                    contentDescription = "arrow"
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                        }

                        Icon(
                            painterResource(R.drawable.baseline_add_24),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ExpandableListSample() {
    val items = listOf("TOEIC900点", "ベース練習", "統計2級")
    Column {
        ExpandableCard(title = "科目", items = items)
    }
}