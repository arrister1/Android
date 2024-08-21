package com.synrgy7team4.feature_transfer.domain.model

data class User (
    val accountNumber: String,
    val accountPin: String,
    val dateOfBirth: String,
    val ektpPhoto: String?,
    val email: String,
    val name: String,
    val noHp: String,
    val noKtp: String,

    )