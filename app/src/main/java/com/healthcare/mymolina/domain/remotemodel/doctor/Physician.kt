package com.healthcare.mymolina.domain.remotemodel.doctor


import com.google.gson.annotations.SerializedName

data class Physician(
    @SerializedName("info")
    val info: Info? = Info(),
    @SerializedName("results")
    val results: List<Result?>? = listOf()
)