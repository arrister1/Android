package com.synrgy7team4.domain.feature_transfer.model.request

data class TransferRequest(
    val accountFrom: String,
    val accountTo: String,
    val amount: Int,
    val description: String,
    val pin: String,
    val datetime: String,
    val destinationBank: String
)