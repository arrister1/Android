package com.synrgy7team4.feature_transfer.domain.model

data class TransferRequest(
    val accountFrom: String,
    val accountTo: String,
    val amount: Int,
    val description: String,
    val pin: String,

    )
