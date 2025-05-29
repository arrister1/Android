package com.synrgy7team4.data.feature_transfer

import com.synrgy7team4.data.feature_transfer.datasource.remote.response.AccountSaveResponse
import com.synrgy7team4.data.feature_transfer.datasource.remote.response.AccountsResponse
import com.synrgy7team4.data.feature_transfer.datasource.remote.response.BalanceGetResponse
import com.synrgy7team4.data.feature_transfer.datasource.remote.response.BalanceSetResponse
import com.synrgy7team4.data.feature_transfer.datasource.remote.response.MutationGetData
import com.synrgy7team4.data.feature_transfer.datasource.remote.response.MutationGetResponse
import com.synrgy7team4.data.feature_transfer.datasource.remote.response.SavedAccountGetData
import com.synrgy7team4.data.feature_transfer.datasource.remote.response.SavedAccountsGetResponse
import com.synrgy7team4.data.feature_transfer.datasource.remote.response.TransferData
import com.synrgy7team4.data.feature_transfer.datasource.remote.response.TransferResponse
import com.synrgy7team4.domain.feature_transfer.model.response.AccountSaveResponseDomain
import com.synrgy7team4.domain.feature_transfer.model.response.AccountsResponseItemDomain
import com.synrgy7team4.domain.feature_transfer.model.response.BalanceGetResponseDomain
import com.synrgy7team4.domain.feature_transfer.model.response.BalanceSetResponseDomain
import com.synrgy7team4.domain.feature_transfer.model.response.MutationGetDataDomain
import com.synrgy7team4.domain.feature_transfer.model.response.MutationGetResponseDomain
import com.synrgy7team4.domain.feature_transfer.model.response.SavedAccountGetDataDomain
import com.synrgy7team4.domain.feature_transfer.model.response.SavedAccountsGetResponseDomain
import com.synrgy7team4.domain.feature_transfer.model.response.TransferDataDomain
import com.synrgy7team4.domain.feature_transfer.model.response.TransferResponseDomain

object FileUtils {
    fun TransferResponse.toDomain(): TransferResponseDomain =
        TransferResponseDomain(
            data = data?.toDomain(),
            message = message,
            success = success
        )

    private fun TransferData.toDomain(): TransferDataDomain =
        TransferDataDomain(
            amount = amount,
            destinationBank = destinationBank,
            description = description,
            type = type,
            accountFrom = accountFrom,
            createdAt = createdAt,
            accountTo = accountTo,
            datetime = datetime,
            balance = balance,
            referenceNumber = referenceNumber,
            nameAccountFrom = nameAccountFrom,
            id = id,
            nameAccountTo = nameAccountTo,
            status = status
        )

    fun BalanceSetResponse.toDomain(): BalanceSetResponseDomain =
        BalanceSetResponseDomain(
            data = data,
            message = message,
            success = success
        )

    fun BalanceGetResponse.toDomain(): BalanceGetResponseDomain =
        BalanceGetResponseDomain(
            data = data,
            message = message,
            success = success
        )

    fun AccountSaveResponse.toDomain(): AccountSaveResponseDomain =
        AccountSaveResponseDomain(
            success = success,
            message = message
        )

    fun SavedAccountsGetResponse.toDomain(): SavedAccountsGetResponseDomain =
        SavedAccountsGetResponseDomain(
            data = data?.map { it?.toDomain() } ?: emptyList(),
            success = success,
            message = message
        )

    private fun SavedAccountGetData.toDomain(): SavedAccountGetDataDomain =
        SavedAccountGetDataDomain(
            name = name,
            id = id,
            accountNumber = accountNumber
        )

    fun MutationGetResponse.toDomain(): MutationGetResponseDomain =
        MutationGetResponseDomain(
            data = data?.toDomain(),
            success = success,
            message = message
        )

    private fun MutationGetData.toDomain(): MutationGetDataDomain =
        MutationGetDataDomain(
            amount = amount,
            currentBalance = currentBalance,
            usernameTo = usernameTo,
            description = description,
            accountFromType = accountToType,
            type = type,
            usernameFrom = usernameFrom,
            datetime = datetime,
            accountTo = accountTo,
            accountToType = accountToType,
            id = id,
            accountFrom = accountFrom,
            status = status
        )

    fun AccountsResponse.toDomain(): AccountsResponseItemDomain =
        AccountsResponseItemDomain(
            balance = balance,
            id = id,
            accountNumber = accountNumber,
            userName = userName,
            userId = userId
        )
}