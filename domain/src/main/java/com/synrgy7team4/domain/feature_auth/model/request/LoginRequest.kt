package com.synrgy7team4.domain.feature_auth.model.request

data class LoginRequest(
    val email: String,
    val password: String
)