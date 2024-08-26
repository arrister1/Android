package com.synrgy7team4.domain.feature_transfer.model.request

data class BalanceSetRequest(
    val accountNumber: String,
    val newBalance: Double
)