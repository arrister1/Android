package com.synrgy7team4.domain.feature_auth.model.request

data class ForgotPasswordRequest(
    val email: String = "",
    val otp: String = "",
    val newPassword: String = "",
)
