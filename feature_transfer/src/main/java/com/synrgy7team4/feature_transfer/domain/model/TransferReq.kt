package com.synrgy7team4.feature_transfer.domain.model

import com.synrgy7team4.feature_transfer.data.remote.request.TransferRequest

data class TransferReq(
    val accountFrom: String,
    val accountTo: String,
    val amount: Int,
    val description: String,
    val pin: String,

    )


