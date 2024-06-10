package com.healthcare.mymolina.domain.usecase

import android.util.Log
import com.healthcare.mymolina.domain.remotemodel.doctor.Doctor
import com.healthcare.mymolina.domain.repository.MolinaRepository
import javax.inject.Inject

class GetPhysicianListUseCase @Inject constructor(private val repository: MolinaRepository) {
    suspend operator fun invoke(): List<Doctor> = requireNotNull(repository.getPhysicianList().body()?.results?.get(0)?.doctors?.filterNotNull())
}