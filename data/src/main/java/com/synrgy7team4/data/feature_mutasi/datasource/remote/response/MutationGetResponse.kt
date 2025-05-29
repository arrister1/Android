package com.synrgy7team4.data.feature_mutasi.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class MutationGetResponse(
	@field:SerializedName("data")
	val data: List<MutationData>?,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class MutationData(
	@field:SerializedName("username_from")
	val usernameFrom: String? = null,

	@field:SerializedName("amount")
	val amount: Double? = null,

	@field:SerializedName("datetime")
	val datetime: String? = null,

	@field:SerializedName("account_to")
	val accountTo: String? = null,

	@field:SerializedName("balance")
	val balance: Double? = null,

	@field:SerializedName("username_to")
	val usernameTo: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("account_from")
	val accountFrom: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)