package com.synrgy7team4.data.feature_auth.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class KtpNumberCheckResponse(
	@field:SerializedName("data")
	val data: String,

	@field:SerializedName("success")
	val success: Boolean
)