package com.synrgy7team4.feature_auth.data.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
	val success: Boolean,
	val message: String,
)
