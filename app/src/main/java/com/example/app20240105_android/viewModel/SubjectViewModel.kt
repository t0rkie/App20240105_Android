package com.example.app20240105_android.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app20240105_android.models.Subject
import com.example.app20240105_android.repositories.SubjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel @Inject constructor(private val subjectRepository: SubjectRepository): ViewModel() {
    private  val _subjects = MutableLiveData<List<Subject>>(mutableListOf())
    val subjects: LiveData<List<Subject>> = _subjects

    fun refreshSubjects() {
        viewModelScope.launch {
            _subjects.value = subjectRepository.getAllSubjects()
        }
    }

    fun addSubject(subject: Subject) {
        viewModelScope.launch {
            subjectRepository.createSubject(subject)
            refreshSubjects()
        }
    }

    fun deleteSubject(subject: Subject) {
        viewModelScope.launch {
            subjectRepository.deleteSubject(subject)
            refreshSubjects()
        }
    }
}