package com.synrgy7team4.data.feature_transfer.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class AccountsResponse(
	@field:SerializedName("balance")
	val balance: Double? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("accountNumber")
	val accountNumber: String? = null,

	@field:SerializedName("userName")
	val userName: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null
)