package com.synrgy7team4.data.feature_auth.datasource.remote.response

data class OtpResponse(

    val success: Boolean,
    val data: String? = null,
    val message: String,
    val errors: String? = null

)


data class OtpData(
    val email: String,
    val otp: String,
)
