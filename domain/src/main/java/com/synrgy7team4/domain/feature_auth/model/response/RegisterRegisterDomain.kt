package com.synrgy7team4.domain.feature_auth.model.response

data class RegisterResponseDomain(
    val data: RegisterDataDomain,
    val success: Boolean,
    val message: String
)

data class RegisterDataDomain(
    val accountNumber: String,
    val noHp: String,
    val dateOfBirth: String,
    val accountPin: String?,
    val noKtp: String,
    val name: String,
    val ektpPhoto: String,
    val email: String
)