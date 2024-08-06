package com.synrgy7team4.feature_auth.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: LoginData? = null,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String? = null
)

data class LoginData(

	@field:SerializedName("refresh_token")
	val refreshToken: String,

	@field:SerializedName("jwt_token")
	val jwtToken: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("email")
	val email: String
)