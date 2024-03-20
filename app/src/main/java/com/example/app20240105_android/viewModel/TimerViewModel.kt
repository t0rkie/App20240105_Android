package com.example.app20240105_android.viewModel

import android.os.Looper
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.app20240105_android.models.Subject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(): ViewModel() {
    private var elapsedTime = mutableStateOf(0)
    private val handler = android.os.Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private var isTimerRunning = mutableStateOf(false)

    // 選択された科目
    var selectedSubject = mutableStateOf(Subject())
    fun startTimer() {
        if (isTimerRunning.value) return
        isTimerRunning.value = true
        runnable = Runnable {
            elapsedTime.value++
            // UI更新
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }

    fun stopTimer() {
        if (!isTimerRunning.value) return

        handler.removeCallbacks(runnable)
        isTimerRunning.value = false
    }

    fun resetTimer() {
        stopTimer()
        elapsedTime.value = 0
    }

    // timeFormatted() -> String
    val timeFormatted: String
        get() {
            val hours = elapsedTime.value / 3600
            val minutes = (elapsedTime.value % 3600) / 60
            val seconds = elapsedTime.value % 60
            return String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }
}