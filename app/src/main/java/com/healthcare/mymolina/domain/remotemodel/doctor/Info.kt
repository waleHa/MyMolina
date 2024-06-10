package com.healthcare.mymolina.domain.remotemodel.doctor


import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("page")
    val page: String? = "",
    @SerializedName("results")
    val results: String? = "",
    @SerializedName("seed")
    val seed: String? = "",
    @SerializedName("time")
    val time: Time? = Time(),
    @SerializedName("version")
    val version: String? = ""
)