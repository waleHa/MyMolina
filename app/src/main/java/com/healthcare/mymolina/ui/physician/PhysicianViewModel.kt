package com.healthcare.mymolina.ui.physician

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthcare.mymolina.domain.remotemodel.doctor.Doctor
import com.healthcare.mymolina.domain.usecase.GetPhysicianListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PhysicianViewModel @Inject constructor(private val useCase: GetPhysicianListUseCase) : ViewModel() {
    private val _physicianListSuccess = MutableStateFlow<List<Doctor>>(emptyList())
    val physicianListSuccess = _physicianListSuccess.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    init {
        getPhysicianList()
    }

    fun getPhysicianList() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                _physicianListSuccess.emit(useCase())
            } catch (e: Exception) {
                // Handle exception
            } finally {
                _loading.value = false
            }
        }
    }
}

