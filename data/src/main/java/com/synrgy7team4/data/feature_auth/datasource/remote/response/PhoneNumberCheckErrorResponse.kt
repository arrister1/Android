package com.synrgy7team4.data.feature_auth.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class PhoneNumberCheckErrorResponse(
	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("errors")
	val errors: String? = null
)