package com.synrgy7team4.feature_transfer.domain.model

data class Transfer(
    val accountFrom: String,
    val accountTo: String,
    val amount: Int,
    val balance: Int,
    val datetime: String,
    val description: String,
    val id: String,
    val nameAccountFrom: String,
    val nameAccountTo: String,
    val status: String,
    val type: String,

    )
