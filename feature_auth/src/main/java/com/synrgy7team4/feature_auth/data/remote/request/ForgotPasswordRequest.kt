package com.synrgy7team4.feature_auth.data.remote.request

data class ForgotPasswordRequest(
    val email: String = "",
    val otp: String = "",
    val newPassword: String = "",
)
