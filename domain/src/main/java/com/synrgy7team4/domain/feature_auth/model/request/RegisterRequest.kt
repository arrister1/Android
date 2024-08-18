package com.synrgy7team4.domain.feature_auth.model.request

data class RegisterRequest(
	val email: String,
	val password: String,
	val name: String,
	val no_hp: String,
	val no_ktp: String,
	val date_of_birth: String,
	val ektp_photo: String,
	val pin: String
)