package com.synrgy7team4.feature_auth.data.remote.retrofit

import retrofit2.http.Field
import java.time.LocalDate
import java.util.Date

data class RegisterBody(
    val email: String,
    val no_hp: String,
    val password: String,
//    val confirm_password: String,
    val no_ktp: String,
    val name: String,
    val date_of_birth: String,
    val pin: String,
//    val confirm_pin: String,
//    val ektp_photo: String,
)
