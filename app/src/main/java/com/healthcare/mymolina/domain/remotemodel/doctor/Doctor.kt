package com.healthcare.mymolina.domain.remotemodel.doctor


import com.google.gson.annotations.SerializedName

data class Doctor(
    @SerializedName("email")
    val email: String? = "",
    @SerializedName("firstName")
    val firstName: String? = "",
    @SerializedName("gender")
    val gender: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("isPhysician")
    val isPhysician: Boolean? = false,
    @SerializedName("lastName")
    val lastName: String? = "",
    @SerializedName("location")
    val location: Location? = Location(),
    @SerializedName("phone")
    val phone: String? = "",
    @SerializedName("profileUrl")
    val profileUrl: String? = "",
    @SerializedName("speciality")
    val speciality: String? = ""
)