package com.healthcare.mymolina.domain.remotemodel.doctor


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("city")
    val city: String? = "",
    @SerializedName("state")
    val state: String? = "",
    @SerializedName("street")
    val street: String? = "",
    @SerializedName("zip")
    val zip: String? = ""
)