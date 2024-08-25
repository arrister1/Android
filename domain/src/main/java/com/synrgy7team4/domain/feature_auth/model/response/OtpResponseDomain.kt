package com.synrgy7team4.domain.feature_auth.model.response

data class OtpResponseDomain(
    val success: Boolean,
    val data: String? = null,
    val message: String,
    val errors: String? = null


)

data class OtpDataDomain(
    val email: String,
    val otp: String,
)

