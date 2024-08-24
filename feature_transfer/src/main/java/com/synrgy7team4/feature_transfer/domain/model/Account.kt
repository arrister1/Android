package com.synrgy7team4.feature_transfer.domain.model

import com.google.gson.annotations.SerializedName

data class Account(
    val accountNumber: String,
    val id: String,
    val name: String,
    )

data class Accounts(
    val id: String,
    val accountNumber: String,
    val balance: Double,
    val userId: String,
    val userName: String
)
