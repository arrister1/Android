package com.synrgy7team4.domain.feature_transfer.model.response

data class MutationGetResponseDomain(
    val data: MutationGetDataDomain? = null,
    val success: Boolean? = null,
    val message: String? = null
)

data class MutationGetDataDomain(
    val amount: Double? = null,
    val currentBalance: Any? = null,
    val usernameTo: String? = null,
    val description: String? = null,
    val accountFromType: Any? = null,
    val type: String? = null,
    val usernameFrom: String? = null,
    val datetime: String? = null,
    val accountTo: String? = null,
    val accountToType: Any? = null,
    val id: String? = null,
    val accountFrom: String? = null,
    val status: String? = null
)