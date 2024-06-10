package com.healthcare.mymolina.data.repository

import com.healthcare.mymolina.data.network.MolinaRemoteService
import com.healthcare.mymolina.domain.remotemodel.doctor.Physician
import com.healthcare.mymolina.domain.repository.MolinaRepository
import retrofit2.Response
import javax.inject.Inject

class MolinaRepositoryImpl @Inject constructor(private val service: MolinaRemoteService):MolinaRepository {
    override suspend fun getPhysicianList(): Response<Physician> = service.getPhysicianList()
}