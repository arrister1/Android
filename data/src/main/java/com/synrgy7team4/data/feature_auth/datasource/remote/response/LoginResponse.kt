package com.synrgy7team4.data.feature_auth.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
	@field:SerializedName("data")
	val data: LoginData,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class LoginData(
	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("jwt_token")
	val jwtToken: String,

	@field:SerializedName("refresh_token")
	val refreshToken: String
)