package com.synrgy7team4.data.feature_transfer.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class TransferResponse(
	@field:SerializedName("data")
	val data: TransferData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class TransferData(
	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("destinationBank")
	val destinationBank: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("accountFrom")
	val accountFrom: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("accountTo")
	val accountTo: String? = null,

	@field:SerializedName("datetime")
	val datetime: String? = null,

	@field:SerializedName("balance")
	val balance: Double? = null,

	@field:SerializedName("referenceNumber")
	val referenceNumber: String? = null,

	@field:SerializedName("nameAccountFrom")
	val nameAccountFrom: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("nameAccountTo")
	val nameAccountTo: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)