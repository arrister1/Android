package com.synrgy7team4.data.feature_transfer.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class AccountSaveResponse(
	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String? = null
)