package com.synrgy7team4.feature_transfer.domain.model


data class TransferRes (
    val data: Transfer?,
    val errors: String?,
    val message: String?,
    val success: Boolean
)