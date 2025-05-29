package com.synrgy7team4.domain.feature_transfer.model.response

data class SavedAccountsGetResponseDomain(
	val data: List<SavedAccountGetDataDomain?>? = emptyList(), // Non-null with default empty list
	val success: Boolean? = false, // Non-null with default value
	val message: String? = "" // Non-null with default empty string
)


data class SavedAccountGetDataDomain(
	val name: String? = "Unknown Name", // Non-null with default value
	val id: String? = "", // Non-null with default value
	val accountNumber: String? = "N/A", // Non-null with default value
	val destinationBank: String? = "Unknown Bank" // Non-null with default value
)
