package com.synrgy7team4.domain.feature_transfer.model.response

data class SavedAccountsGetResponseDomain(
	val data: List<SavedAccountGetDataDomain?>? = null,
	val success: Boolean? = null,
	val message: String? = null
)

data class SavedAccountGetDataDomain(
	val name: String? = null,
	val id: String? = null,
	val accountNumber: String? = null,
	val destinationBank: String? = null

)