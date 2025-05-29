package com.synrgy7team4.data.feature_transfer.datasource.remote

import com.synrgy7team4.data.feature_transfer.datasource.remote.response.BalanceGetResponse
import com.synrgy7team4.data.feature_transfer.datasource.remote.response.BalanceSetResponse
import com.synrgy7team4.data.feature_transfer.datasource.remote.response.AccountSaveResponse
import com.synrgy7team4.data.feature_transfer.datasource.remote.response.AccountsResponse
import com.synrgy7team4.data.feature_transfer.datasource.remote.response.MutationGetResponse
import com.synrgy7team4.data.feature_transfer.datasource.remote.response.SavedAccountsGetResponse
import com.synrgy7team4.data.feature_transfer.datasource.remote.response.TransferResponse
import com.synrgy7team4.data.feature_transfer.datasource.remote.retrofit.ApiService
import com.synrgy7team4.domain.feature_transfer.model.request.BalanceSetRequest
import com.synrgy7team4.domain.feature_transfer.model.request.AccountSaveRequest
import com.synrgy7team4.domain.feature_transfer.model.request.TransferRequest
import com.synrgy7team4.domain.feature_transfer.usecase.HttpExceptionUseCase
import retrofit2.HttpException

class RemoteDatasource(private val apiService: ApiService) {
    suspend fun transfer(jwtToken: String, transferRequest: TransferRequest): TransferResponse =
        try {
            apiService.transfer(jwtToken, transferRequest)
        } catch (e: HttpException) {
            val errorCode = e.response()?.code()
            if (errorCode == 403) {
                throw HttpExceptionUseCase(e, "403 Forbidden")
            } else {
                throw HttpExceptionUseCase(e)
            }
        }

    suspend fun setBalance(
        jwtToken: String,
        balanceRequest: BalanceSetRequest
    ): BalanceSetResponse =
        try {
            apiService.setBalance(jwtToken, balanceRequest)
        } catch (e: HttpException) {
            val errorCode = e.response()?.code()
            if (errorCode == 403) {
                throw HttpExceptionUseCase(e, "403 Forbidden")
            } else {
                throw HttpExceptionUseCase(e)
            }
        }

    suspend fun getBalance(jwtToken: String, accountNumber: String): BalanceGetResponse =
        try {
            apiService.getBalance(jwtToken, accountNumber)
        } catch (e: HttpException) {
            val errorCode = e.response()?.code()
            if (errorCode == 403) {
                throw HttpExceptionUseCase(e, "403 Forbidden")
            } else {
                throw HttpExceptionUseCase(e)
            }
        }

    suspend fun saveAccount(
        jwtToken: String,
        accountSaveRequest: AccountSaveRequest
    ): AccountSaveResponse =
        try {
            apiService.saveAccount(jwtToken, accountSaveRequest)
        } catch (e: HttpException) {
            val errorCode = e.response()?.code()
            if (errorCode == 403) {
                throw HttpExceptionUseCase(e, "403 Forbidden")
            } else {
                throw HttpExceptionUseCase(e)
            }
        }

    suspend fun getSavedAccounts(jwtToken: String): SavedAccountsGetResponse =
        try {
            apiService.getSavedAccounts(jwtToken)
        } catch (e: HttpException) {
            val errorCode = e.response()?.code()
            if (errorCode == 403) {
                throw HttpExceptionUseCase(e, "403 Forbidden")
            } else {
                throw HttpExceptionUseCase(e)
            }
        }

    suspend fun getMutation(jwtToken: String, id: String): MutationGetResponse =
        try {
            apiService.getMutation(jwtToken, id)
        } catch (e: HttpException) {
            val errorCode = e.response()?.code()
            if (errorCode == 403) {
                throw HttpExceptionUseCase(e, "403 Forbidden")
            } else {
                throw HttpExceptionUseCase(e)
            }
        }

    suspend fun checkAccount(jwtToken: String): List<AccountsResponse> =
        try {
            apiService.checkAccount(jwtToken)
        } catch (e: HttpException) {
            val errorCode = e.response()?.code()
            if (errorCode == 403) {
                throw HttpExceptionUseCase(e, "403 Forbidden")
            } else {
                throw HttpExceptionUseCase(e)
            }
        }
}