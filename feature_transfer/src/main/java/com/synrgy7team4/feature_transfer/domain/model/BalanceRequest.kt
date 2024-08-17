package com.synrgy7team4.feature_transfer.domain.model

data class BalanceRequest(
    val accountNumber: String,
    val newBalance : Int
)
