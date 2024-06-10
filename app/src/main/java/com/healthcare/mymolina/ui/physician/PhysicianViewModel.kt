package com.healthcare.mymolina.ui.physician

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthcare.mymolina.domain.remotemodel.doctor.Doctor
import com.healthcare.mymolina.domain.usecase.GetPhysicianListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject


@HiltViewModel
class PhysicianViewModel @Inject constructor(private val useCase: GetPhysicianListUseCase) : ViewModel() {
    private val _physicianListSuccess = MutableStateFlow<List<Doctor>>(emptyList())
    val physicianListSuccess = _physicianListSuccess.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init {
        getPhysicianList()
    }

    fun getPhysicianList() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                _physicianListSuccess.emit(useCase())
            } catch (e: UnknownHostException) {
                _error.value = "Network error: Unable to resolve host. Please check your internet connection."
                Log.e("TAG: PhysicianViewModel","Network error: Unable to resolve host. Please check your internet connection.")
            } catch (e: Exception) {
                _error.value = "An unexpected error occurred: ${e.localizedMessage}"
                Log.e("TAG: PhysicianViewModel","An unexpected error occurred: ${e.localizedMessage}")
            } finally {
                _loading.value = false
            }
        }
    }
}


