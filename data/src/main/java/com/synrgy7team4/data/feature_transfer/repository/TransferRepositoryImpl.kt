package com.synrgy7team4.data.feature_transfer.repository

import com.synrgy7team4.data.feature_transfer.FileUtils.toDomain
import com.synrgy7team4.data.feature_transfer.datasource.remote.RemoteDatasource
import com.synrgy7team4.domain.feature_transfer.model.request.BalanceSetRequest
import com.synrgy7team4.domain.feature_transfer.model.request.AccountSaveRequest
import com.synrgy7team4.domain.feature_transfer.model.request.TransferRequest
import com.synrgy7team4.domain.feature_transfer.model.response.BalanceGetResponseDomain
import com.synrgy7team4.domain.feature_transfer.model.response.BalanceSetResponseDomain
import com.synrgy7team4.domain.feature_transfer.model.response.AccountSaveResponseDomain
import com.synrgy7team4.domain.feature_transfer.model.response.MutationGetResponseDomain
import com.synrgy7team4.domain.feature_transfer.model.response.SavedAccountsGetResponseDomain
import com.synrgy7team4.domain.feature_transfer.model.response.TransferResponseDomain
import com.synrgy7team4.domain.feature_transfer.repository.TransferRepository

class TransferRepositoryImpl(private val remoteDatasource: RemoteDatasource) : TransferRepository {
    override suspend fun transfer(jwtToken: String, transferRequest: TransferRequest): TransferResponseDomain =
        remoteDatasource.transfer(jwtToken, transferRequest).toDomain()

    override suspend fun setBalance(jwtToken: String, balanceSetRequest: BalanceSetRequest): BalanceSetResponseDomain =
        remoteDatasource.setBalance(jwtToken, balanceSetRequest).toDomain()

    override suspend fun getBalance(jwtToken: String, accountNumber: String): BalanceGetResponseDomain =
        remoteDatasource.getBalance(jwtToken, accountNumber).toDomain()

    override suspend fun saveAccount(jwtToken: String, accountSaveRequest: AccountSaveRequest): AccountSaveResponseDomain =
        remoteDatasource.saveAccount(jwtToken, accountSaveRequest).toDomain()

    override suspend fun getSavedAccounts(jwtToken: String): SavedAccountsGetResponseDomain =
        remoteDatasource.getSavedAccounts(jwtToken).toDomain()

    override suspend fun getMutation(jwtToken: String, id: String): MutationGetResponseDomain =
        remoteDatasource.getMutation(jwtToken, id).toDomain()
}