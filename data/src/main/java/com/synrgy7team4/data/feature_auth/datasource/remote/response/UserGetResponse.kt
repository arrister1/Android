package com.synrgy7team4.data.feature_auth.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class UserGetResponse(
	@field:SerializedName("data")
	val data: UserData,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class UserData(
	@field:SerializedName("account_number")
	val accountNumber: String? = null,

	@field:SerializedName("no_hp")
	val noHp: String? = null,

	@field:SerializedName("date_of_birth")
	val dateOfBirth: String? = null,

	@field:SerializedName("account_pin")
	val accountPin: String? = null,

	@field:SerializedName("no_ktp")
	val noKtp: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("ektp_photo")
	val ektpPhoto: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
