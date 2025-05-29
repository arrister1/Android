package com.synrgy7team4.domain.feature_transfer.repository

import com.synrgy7team4.domain.feature_transfer.model.request.BalanceSetRequest
import com.synrgy7team4.domain.feature_transfer.model.request.AccountSaveRequest
import com.synrgy7team4.domain.feature_transfer.model.request.TransferRequest
import com.synrgy7team4.domain.feature_transfer.model.response.BalanceGetResponseDomain
import com.synrgy7team4.domain.feature_transfer.model.response.BalanceSetResponseDomain
import com.synrgy7team4.domain.feature_transfer.model.response.AccountSaveResponseDomain
import com.synrgy7team4.domain.feature_transfer.model.response.AccountsResponseItemDomain
import com.synrgy7team4.domain.feature_transfer.model.response.MutationGetResponseDomain
import com.synrgy7team4.domain.feature_transfer.model.response.SavedAccountsGetResponseDomain
import com.synrgy7team4.domain.feature_transfer.model.response.TransferResponseDomain

interface TransferRepository {
    suspend fun transfer(jwtToken: String, transferRequest: TransferRequest): TransferResponseDomain
    suspend fun setBalance(jwtToken: String, balanceSetRequest: BalanceSetRequest): BalanceSetResponseDomain
    suspend fun getBalance(jwtToken: String, accountNumber: String): BalanceGetResponseDomain
    suspend fun saveAccount(jwtToken: String, accountSaveRequest: AccountSaveRequest): AccountSaveResponseDomain
    suspend fun getSavedAccounts(jwtToken: String): SavedAccountsGetResponseDomain
    suspend fun getMutation(jwtToken: String, id: String): MutationGetResponseDomain
    suspend fun checkAccount(token: String): List<AccountsResponseItemDomain>
}