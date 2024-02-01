package com.example.app20240105_android

import android.os.Looper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(): ViewModel() {
    private var elapsedTime = mutableStateOf(0)
    private var timer by mutableStateOf(0)
    private val handler = android.os.Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    fun startTimer() {
        runnable = Runnable {
            elapsedTime.value++
            // UI更新
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }

    fun stopTimer() {
        handler.removeCallbacks(runnable)
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