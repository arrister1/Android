package com.synrgy7team4.feature_transfer.data.remote.repository

import com.synrgy7team4.common.Resource
import com.synrgy7team4.feature_transfer.data.remote.RemoteDataSource
import com.synrgy7team4.feature_transfer.domain.model.Account
import com.synrgy7team4.feature_transfer.domain.model.AccountRequest
import com.synrgy7team4.feature_transfer.domain.model.BalanceRequest
import com.synrgy7team4.feature_transfer.domain.model.Transfer
import com.synrgy7team4.feature_transfer.domain.model.TransferRequest
import com.synrgy7team4.feature_transfer.domain.model.User
import com.synrgy7team4.feature_transfer.domain.repository.TransferRepository
import kotlinx.coroutines.flow.Flow

class TransferRepositoryImpl(private val remoteDataSource: RemoteDataSource): TransferRepository {
    override suspend fun postTransfer(request: TransferRequest): Flow<Resource<Transfer>> {
        TODO("Not yet implemented")
    }

    override suspend fun getBalance(accountNumber: String): Flow<Resource<Int>> {
        TODO("Not yet implemented")
    }

    override suspend fun postBalance(request: BalanceRequest): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAccountList(): Flow<Resource<List<Account>>> {
        TODO("Not yet implemented")
    }

    override suspend fun postAccountList(request: AccountRequest): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserData(): Flow<Resource<User>> {
        TODO("Not yet implemented")
    }
}