package com.synrgy7team4.domain.feature_auth.model.request

data class SendOtpRequest(
    val email: String,
    val noHP: String,
)