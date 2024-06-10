package com.healthcare.mymolina.data.network

import com.healthcare.mymolina.domain.remotemodel.doctor.Physician
import retrofit2.Response
import retrofit2.http.GET

interface MolinaRemoteService {
    @GET("5fd63a88c60f95f4b09e8557145c4536")
    suspend fun getPhysicianList(): Response<Physician>
}