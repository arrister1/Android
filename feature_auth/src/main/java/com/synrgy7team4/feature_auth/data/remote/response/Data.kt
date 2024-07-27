package com.synrgy7team4.feature_auth.data.remote.response


import com.google.gson.annotations.SerializedName

//Data is response from Register
data class Data(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("no_ktp")
    val noKtp: String,
    @SerializedName("no_hp")
    val noHp: String,
    @SerializedName("date_of_birth")
    val dateOfBirth: String,
)