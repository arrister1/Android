package com.synrgy7team4.feature_transfer.domain.usecase

import com.synrgy7team4.common.Resource
import com.synrgy7team4.feature_transfer.data.remote.response.account.AccountData
import com.synrgy7team4.feature_transfer.domain.model.Account
import com.synrgy7team4.feature_transfer.domain.model.AccountReq
import com.synrgy7team4.feature_transfer.domain.model.BalanceReq
import com.synrgy7team4.feature_transfer.domain.model.Transfer
import com.synrgy7team4.feature_transfer.domain.model.TransferReq
import com.synrgy7team4.feature_transfer.domain.model.User
import com.synrgy7team4.feature_transfer.domain.repository.TransferRepository
import kotlinx.coroutines.flow.Flow

class TransferUseCase(private val repository: TransferRepository) {

    fun postTransfer(request: TransferReq): Flow<Resource<Transfer>>{
        return repository.postTransfer(request)
    }

    fun getBalance(accountNumber: String): Flow<Resource<Int>>{
        return repository.getBalance(accountNumber)
    }

    fun postBalance(request: BalanceReq): Flow<Resource<Unit>> {
        return repository.postBalance(request)
    }

    fun getAccountList(): Flow<Resource<List<AccountData>>>{
        return repository.getAccountList()
    }

    fun postAccountList(request: AccountReq): Flow<Resource<Unit>>{
        return  repository.postAccountList(request)
    }

    fun getUserData(): Flow<Resource<User>>{
        return repository.getUserData()
    }
}