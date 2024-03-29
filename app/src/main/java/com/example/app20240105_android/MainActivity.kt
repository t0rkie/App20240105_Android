package com.example.app20240105_android

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app20240105_android.components.AccordionList
import com.example.app20240105_android.viewModel.MainViewModel
import com.example.app20240105_android.viewModel.StudyLogViewModel
import com.example.app20240105_android.viewModel.TimerViewModel
import com.example.app20240105_android.views.RecordView
import com.example.app20240105_android.views.StudyLogView
import com.example.app20240105_android.views.TimerView
import com.example.app20240105_android.ui.theme.App20240105_AndroidTheme
import com.example.app20240105_android.viewModel.SubjectViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.LaunchedEffect

data class BottomNavigationItem(
    val title: String,
    val destination: String,
    val icon: Int,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App20240105_AndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent() {

    val timerViewModel = hiltViewModel<TimerViewModel>()
    val mainViewModel = hiltViewModel<MainViewModel>()
    val studyLogViewModel = hiltViewModel<StudyLogViewModel>()

    // navigationを追加
    val navController = rememberNavController()
    val pageIndex by mainViewModel.pageIndex.observeAsState()

    LaunchedEffect(Unit) {
        studyLogViewModel.refreshLogs()
    }

    val items = listOf(
        BottomNavigationItem(
            title = "タイマー",
            destination = "TimerView",
            icon = R.drawable.baseline_av_timer_24,
            hasNews = false,
        ),
        BottomNavigationItem(
            title = "記録",
            destination = "StudyLogView",
            icon = R.drawable.baseline_text_snippet_24,
            hasNews = false,
        ),
        BottomNavigationItem(
            title = "レポート",
            destination = "ReportView",
            icon = R.drawable.baseline_bar_chart_24,
            hasNews = false,
            badgeCount = 45
        ),
        BottomNavigationItem(
            title = "ホーム",
            destination = "HomeView",
            icon = R.drawable.baseline_home_24,
            hasNews = true,
        ),
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = index == pageIndex,
                        onClick = {
                            mainViewModel.updatePageIndex(index)
                            navController.navigate(item.destination)
                        },
                        label = { Text(text = item.title) },
                        alwaysShowLabel = true,
                        icon = {
                            Icon(
                                painterResource(item.icon),
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
            startDestination = items[pageIndex ?: 0].destination
        ) {
            composable("TimerView") {
                TimerView(navController, timerViewModel)
//                TimerView(navController)
            }
            composable("ReportView") {
                ReportView("Chat", Modifier.padding(padding))
            }
            composable("HomeView") {
                HomeView()
            }
            composable("RecordView") {
                RecordView(navController, mainViewModel, timerViewModel)
//                RecordView(navController)
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
fun HomeView() {
    val subjectViewModel = hiltViewModel<SubjectViewModel>()
    LaunchedEffect(Unit) { subjectViewModel.refreshSubjects() }
    val subjects by subjectViewModel.subjects.observeAsState()

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
            AccordionList(title = "科目", items = subjects)
        }
    }
}
