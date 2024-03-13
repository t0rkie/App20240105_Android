package com.example.app20240105_android.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app20240105_android.models.StudyLog
import com.example.app20240105_android.repositories.StudyLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudyLogViewModel @Inject constructor(private val studyLogRepository: StudyLogRepository): ViewModel() {
    private val _logs = MutableLiveData<List<StudyLog>>(mutableListOf())
    val logs: LiveData<List<StudyLog>> = _logs

    fun refreshLogs() {
        viewModelScope.launch {
            _logs.value = studyLogRepository.getAllStudyLog()
        }
    }

    fun addLog(log: StudyLog) {
        viewModelScope.launch {
            // TODO
//            val log = StudyLog()
//            log.studyTime = 60
//            log.studyTimeStr = "00:10:00"
//            log.memo = "test"
//            log.subjectId = 1
            studyLogRepository.createStudyLog(log)
            refreshLogs()
        }
    }

    fun updateLog(log: StudyLog) {
        viewModelScope.launch {
            studyLogRepository.updateStudyLog(log)
            refreshLogs()
        }
    }

    fun deleteLog(log: StudyLog) {
        viewModelScope.launch {
            studyLogRepository.deleteStudyLog(log)
            refreshLogs()
        }
    }
}