package com.healthcare.mymolina.domain.remotemodel.doctor


import com.google.gson.annotations.SerializedName

data class Time(
    @SerializedName("generate")
    val generate: Int? = 0,
    @SerializedName("instruct")
    val instruct: Int? = 0
)