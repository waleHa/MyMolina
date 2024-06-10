package com.healthcare.mymolina.domain.repository

import com.healthcare.mymolina.domain.remotemodel.doctor.Physician
import retrofit2.Response

interface MolinaRepository {
    suspend fun getPhysicianList(): Response<Physician>
}