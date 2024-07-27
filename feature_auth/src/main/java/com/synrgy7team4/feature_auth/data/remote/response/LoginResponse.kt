package com.synrgy7team4.feature_auth.data.remote.response


import com.google.gson.annotations.SerializedName


//Ga jadi pake ini bangg

data class LoginResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("token")
    val token: String
)
//Ga jadi pake ini bang