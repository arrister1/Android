package com.synrgy7team4.feature_transfer.domain.repository

import com.synrgy7team4.common.Resource
import com.synrgy7team4.feature_transfer.domain.model.Account
import com.synrgy7team4.feature_transfer.domain.model.AccountRequest
import com.synrgy7team4.feature_transfer.domain.model.BalanceRequest
import com.synrgy7team4.feature_transfer.domain.model.Transfer
import com.synrgy7team4.feature_transfer.domain.model.TransferRequest
import com.synrgy7team4.feature_transfer.domain.model.User
import kotlinx.coroutines.flow.Flow

interface TransferRepository {
    suspend fun postTransfer(request: TransferRequest): Flow<Resource<Transfer>>
    suspend fun getBalance(accountNumber: String): Flow<Resource<Int>>
    suspend fun postBalance(request: BalanceRequest): Flow<Resource<Unit>>
    suspend fun getAccountList(): Flow<Resource<List<Account>>>
    suspend fun postAccountList(request: AccountRequest):Flow<Resource<Unit>>
    suspend fun getUserData(): Flow<Resource<User>>

}