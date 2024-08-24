package com.synrgy7team4.feature_transfer.domain.repository

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
import kotlinx.coroutines.flow.Flow

interface TransferRepository {
    fun postTransfer(token: String, request: TransferReq): Flow<Resource<TransferRes>>
    suspend fun postTransferSched(token: String, transScheduleRequest: TransSchedule): TransSchedData
    suspend fun getUserData(token: String): UserResponse
     suspend fun postAccount(token: String, accountRequest: AccountRequest): AccountResponse
     suspend fun getAccountList(token: String): AccountResponse
     suspend fun getMutation(token: String, accountNumber: String): MutationsResponse
     suspend fun postBalance(token: String, balanceRequest: BalanceRequest): BalanceResponse
     suspend fun getBalance(token: String, accountNumber: String): BalanceResponse
     suspend fun cekAccount(token: String): List<Accounts>
    /*fun getBalance(accountNumber: String): Flow<Resource<Int>>
    fun postBalance(request: BalanceReq): Flow<Resource<Unit>>
    fun getAccountList(): Flow<Resource<List<AccountData>>>
   fun postAccountList(request: AccountReq):Flow<Resource<Unit>>*/

}