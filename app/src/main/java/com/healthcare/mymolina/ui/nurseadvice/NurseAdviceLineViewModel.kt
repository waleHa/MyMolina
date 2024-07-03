package com.healthcare.mymolina.ui.nurseadvice


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NurseAdviceLineViewModel @Inject constructor() : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _loading.value = true
            delay(2000) // Simulate a network call
            _loading.value = false
        }
    }
}
