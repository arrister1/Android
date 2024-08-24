package com.synrgy7team4.feature_transfer.domain.usecase

import com.synrgy7team4.common.Resource
import com.synrgy7team4.feature_transfer.data.remote.request.AccountRequest
import com.synrgy7team4.feature_transfer.data.remote.request.BalanceRequest
import com.synrgy7team4.feature_transfer.data.remote.request.TransSchedule
import com.synrgy7team4.feature_transfer.data.remote.response.account.AccountData
import com.synrgy7team4.feature_transfer.data.remote.response.account.AccountResponse
import com.synrgy7team4.feature_transfer.data.remote.response.account.Accounts
import com.synrgy7team4.feature_transfer.data.remote.response.balance.BalanceResponse
import com.synrgy7team4.feature_transfer.data.remote.response.mutations.Data
import com.synrgy7team4.feature_transfer.data.remote.response.mutations.MutationsResponse
import com.synrgy7team4.feature_transfer.data.remote.response.transschedule.TransSchedData
import com.synrgy7team4.feature_transfer.data.remote.response.user.UserResponse
import com.synrgy7team4.feature_transfer.domain.model.Account
import com.synrgy7team4.feature_transfer.domain.model.AccountReq
import com.synrgy7team4.feature_transfer.domain.model.BalanceReq
import com.synrgy7team4.feature_transfer.domain.model.Transfer
import com.synrgy7team4.feature_transfer.domain.model.TransferReq
import com.synrgy7team4.feature_transfer.domain.model.TransferRes
import com.synrgy7team4.feature_transfer.domain.model.User
import com.synrgy7team4.feature_transfer.domain.repository.TransferRepository
import kotlinx.coroutines.flow.Flow

class TransferUseCase(private val repository: TransferRepository) {

    fun postTransfer(token: String, request: TransferReq): Flow<Resource<TransferRes>>{
        return repository.postTransfer(token, request)
    }

     suspend fun postTransferSched(token: String, transScheduleRequest: TransSchedule): TransSchedData {
        return repository.postTransferSched(token, transScheduleRequest)
    }

     suspend fun getUserData(token: String): UserResponse {
        return repository.getUserData(token)
    }

     suspend fun getBalance(token: String, accountNumber: String): BalanceResponse {
        return repository.getBalance(token, accountNumber)
    }

     suspend fun postBalance(token: String, balanceRequest: BalanceRequest): BalanceResponse {
        return repository.postBalance(token, balanceRequest)
    }

     suspend fun getMutation(token: String, accountNumber: String): MutationsResponse {
        return repository.getMutation(token, accountNumber)
    }

     suspend fun getAccountList(token: String): AccountResponse {
        return repository.getAccountList(token)
    }

     suspend fun postAccount(token: String, accountRequest: AccountRequest): AccountResponse {
        return repository.postAccount(token, accountRequest)
    }

    suspend fun cekAccount(token: String): List<Accounts> {
        return repository.cekAccount(token)
    }

    /*fun getBalance(accountNumber: String): Flow<Resource<Int>>{
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
    }*/
}