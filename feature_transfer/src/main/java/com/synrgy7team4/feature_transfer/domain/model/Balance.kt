package com.synrgy7team4.feature_transfer.domain.model

data class Balance(
    val `data`: Int,
    val errors: Int,
    val message : String,
    val success: Boolean,

    )
