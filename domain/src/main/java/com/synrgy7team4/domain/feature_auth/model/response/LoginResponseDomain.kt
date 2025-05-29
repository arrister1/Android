package com.synrgy7team4.domain.feature_auth.model.response

data class LoginResponseDomain(
    val data: LoginDataDomain,
    val success: Boolean,
    val message: String
)

data class LoginDataDomain(
    val email: String,
    val name: String,
    val jwtToken: String,
    val refreshToken: String
)
