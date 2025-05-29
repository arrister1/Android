package com.synrgy7team4.data.feature_auth.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("data")
	val data: RegisterData,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class RegisterData(

	@field:SerializedName("account_number")
	val accountNumber: String,

	@field:SerializedName("no_hp")
	val noHp: String,

	@field:SerializedName("date_of_birth")
	val dateOfBirth: String,

	@field:SerializedName("account_pin")
	val accountPin: String?,

	@field:SerializedName("no_ktp")
	val noKtp: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("ektp_photo")
	val ektpPhoto: String,

	@field:SerializedName("email")
	val email: String
)