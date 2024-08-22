package com.synrgy7team4.feature_transfer.domain.repository

import com.synrgy7team4.common.Resource
import com.synrgy7team4.feature_transfer.domain.model.Account
import com.synrgy7team4.feature_transfer.domain.model.AccountReq
import com.synrgy7team4.feature_transfer.domain.model.BalanceReq
import com.synrgy7team4.feature_transfer.domain.model.Transfer
import com.synrgy7team4.feature_transfer.domain.model.TransferReq
import com.synrgy7team4.feature_transfer.domain.model.TransferRes
import com.synrgy7team4.feature_transfer.domain.model.User
import kotlinx.coroutines.flow.Flow

interface TransferRepository {
    fun postTransfer(request: TransferReq): Flow<Resource<TransferRes>>
    fun getBalance(accountNumber: String): Flow<Resource<Int>>
     fun postBalance(request: BalanceReq): Flow<Resource<Unit>>
     fun getAccountList(): Flow<Resource<List<Account>>>
    fun postAccountList(request: AccountReq):Flow<Resource<Unit>>
     fun getUserData(): Flow<Resource<User>>

}