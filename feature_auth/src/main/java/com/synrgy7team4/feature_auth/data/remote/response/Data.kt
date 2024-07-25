package com.synrgy7team4.feature_auth.data.remote.response


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("date_of_birth")
    val dateOfBirth: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("no_hp")
    val noHp: String,
    @SerializedName("no_ktp")
    val noKtp: String
)