package com.healthcare.mymolina.ui.branch.viewmodel


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
import javax.inject.Inject

@HiltViewModel
class BranchLocatorViewModel @Inject constructor(private val useCase: GetPhysicianListUseCase) : ViewModel() {
    private val _branchList = MutableStateFlow<List<Doctor>>(emptyList())
    val branchList = _branchList.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    init {
        fetchBranches()
    }

    private fun fetchBranches() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val branches = useCase()
                _branchList.emit(branches)
                Log.i("TAG:BranchLocatorViewModel",branches.toString())
            } catch (e: Exception) {
                // Handle exception
                Log.i("TAG E:BranchLocatorViewModel",e.toString())
            } finally {
                _loading.value = false
            }
        }
    }
}


