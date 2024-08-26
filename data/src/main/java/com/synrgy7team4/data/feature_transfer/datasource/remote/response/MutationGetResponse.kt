package com.synrgy7team4.data.feature_transfer.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class MutationGetResponse(
	@field:SerializedName("data")
	val data: MutationGetData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class MutationGetData(
	@field:SerializedName("amount")
	val amount: Double? = null,

	@field:SerializedName("currentBalance")
	val currentBalance: Any? = null,

	@field:SerializedName("username_to")
	val usernameTo: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("accountFromType")
	val accountFromType: Any? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("username_from")
	val usernameFrom: String? = null,

	@field:SerializedName("datetime")
	val datetime: String? = null,

	@field:SerializedName("account_to")
	val accountTo: String? = null,

	@field:SerializedName("accountToType")
	val accountToType: Any? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("account_from")
	val accountFrom: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)