package com.synrgy7team4.domain.feature_transfer.model.response

data class BalanceGetResponseDomain(
    val data: Double,
    val message: String,
    val success: Boolean
)