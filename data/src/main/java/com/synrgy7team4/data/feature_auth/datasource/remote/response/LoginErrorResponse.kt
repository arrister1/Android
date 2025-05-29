package com.synrgy7team4.data.feature_auth.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class LoginErrorResponse(
	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("errors")
	val errors: String? = null
)