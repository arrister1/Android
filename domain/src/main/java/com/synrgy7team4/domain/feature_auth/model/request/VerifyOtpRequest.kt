package com.synrgy7team4.domain.feature_auth.model.request

data class VerifyOtpRequest(
    val email: String,
    val otp: String
)
