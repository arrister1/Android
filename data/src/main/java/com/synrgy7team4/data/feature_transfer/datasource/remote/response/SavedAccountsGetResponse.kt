package com.synrgy7team4.data.feature_transfer.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class SavedAccountsGetResponse(
	@field:SerializedName("data")
	val data: List<SavedAccountGetData?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class SavedAccountGetData(
	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("accountNumber")
	val accountNumber: String? = null
)