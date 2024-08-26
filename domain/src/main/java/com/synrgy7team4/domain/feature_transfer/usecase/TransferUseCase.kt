package com.synrgy7team4.domain.feature_transfer.usecase

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

class TransferUseCase(private val transferRepository: TransferRepository) {
    suspend fun transfer(jwtToken: String, transferRequest: TransferRequest): TransferResponseDomain =
        transferRepository.transfer(jwtToken, transferRequest)

    suspend fun setBalance(jwtToken: String, balanceRequest: BalanceSetRequest): BalanceSetResponseDomain =
        transferRepository.setBalance(jwtToken, balanceRequest)

    suspend fun getBalance(jwtToken: String, accountNumber: String): BalanceGetResponseDomain =
        transferRepository.getBalance(jwtToken, accountNumber)

    suspend fun saveAccount(jwtToken: String, accountSaveRequest: AccountSaveRequest): AccountSaveResponseDomain =
        transferRepository.saveAccount(jwtToken, accountSaveRequest)

    suspend fun getSavedAccounts(jwtToken: String): SavedAccountsGetResponseDomain =
        transferRepository.getSavedAccounts(jwtToken)

    suspend fun getMutation(jwtToken: String, id: String): MutationGetResponseDomain =
        transferRepository.getMutation(jwtToken, id)
}