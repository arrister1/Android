package com.synrgy7team4.domain.feature_dashboard.model.request

data class BalanceSetRequest(
    val accountNumber: String,
    val newBalance: Double
)