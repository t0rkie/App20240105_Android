package com.example.app20240105_android.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {
    init {
        Log.d("MainViewModel", "ViewModel initialized")
    }
    var isShowDialog by mutableStateOf(false)

    private val _pageIndex = MutableLiveData(0)
    val pageIndex: LiveData<Int> = _pageIndex
    fun updatePageIndex(newIndex: Int) {
        _pageIndex.value = newIndex
    }
}