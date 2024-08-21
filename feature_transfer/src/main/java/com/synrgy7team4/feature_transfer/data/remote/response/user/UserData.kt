package com.synrgy7team4.feature_transfer.data.remote.response.user


import com.google.gson.annotations.SerializedName
import com.synrgy7team4.feature_transfer.domain.model.User

data class UserData(
    @SerializedName("account_number")
    val accountNumber: String,
    @SerializedName("account_pin")
    val accountPin: String,
    @SerializedName("date_of_birth")
    val dateOfBirth: String,
    @SerializedName("ektp_photo")
    val ektpPhoto: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("no_hp")
    val noHp: String,
    @SerializedName("no_ktp")
    val noKtp: String
)

fun UserData.toDomain(): User{
    return User(
        accountNumber = accountNumber,
        accountPin = accountPin,
        dateOfBirth = dateOfBirth,
        ektpPhoto = ektpPhoto,
        email = email,
        name = name,
        noHp = noHp,
        noKtp = noKtp
    )
}