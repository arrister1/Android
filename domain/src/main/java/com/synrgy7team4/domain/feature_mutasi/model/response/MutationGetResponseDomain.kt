package com.synrgy7team4.domain.feature_mutasi.model.response

data class MutationGetResponseDomain(
	val data: List<MutationDataDomain>?,
	val success: Boolean,
	val message: String
)

data class MutationDataDomain(
	val usernameFrom: String,
	val amount: Double,
	val datetime: String,
	val accountTo: String,
	val balance: Double,
	val usernameTo: String,
	val description: String,
	val id: String,
	val accountFrom: String,
	val type: String,
	val status: String
)