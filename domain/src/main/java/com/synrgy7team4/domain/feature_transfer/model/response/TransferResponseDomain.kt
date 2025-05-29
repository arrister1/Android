package com.synrgy7team4.domain.feature_transfer.model.response

data class TransferResponseDomain(
	val data: TransferDataDomain? = null,
	val success: Boolean? = null,
	val message: String? = null
)

data class TransferDataDomain(
	val amount: Int? = null,
	val destinationBank: String? = null,
	val description: String? = null,
	val type: String? = null,
	val accountFrom: String? = null,
	val createdAt: String? = null,
	val accountTo: String? = null,
	val datetime: String? = null,
	val balance: Double? = null,
	val referenceNumber: String? = null,
	val nameAccountFrom: String? = null,
	val id: String? = null,
	val nameAccountTo: String? = null,
	val status: String? = null
)