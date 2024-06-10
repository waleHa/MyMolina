package com.healthcare.mymolina.domain.remotemodel.doctor


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("doctors")
    val doctors: List<Doctor?>? = listOf()
)