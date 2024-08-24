package com.synrgy7team4.feature_transfer.data.remote

import android.util.Log
import com.google.gson.Gson
import com.synrgy7team4.common.Resource
import com.synrgy7team4.feature_transfer.data.remote.network.ApiService
import com.synrgy7team4.feature_transfer.data.remote.request.AccountRequest
import com.synrgy7team4.feature_transfer.data.remote.request.BalanceRequest
import com.synrgy7team4.feature_transfer.data.remote.request.TransSchedule
import com.synrgy7team4.feature_transfer.data.remote.request.TransferRequest
import com.synrgy7team4.feature_transfer.data.remote.response.account.AccountData
import com.synrgy7team4.feature_transfer.data.remote.response.account.AccountResponse
import com.synrgy7team4.feature_transfer.data.remote.response.account.Accounts
import com.synrgy7team4.feature_transfer.data.remote.response.balance.BalanceResponse
import com.synrgy7team4.feature_transfer.data.remote.response.mutations.Data
import com.synrgy7team4.feature_transfer.data.remote.response.mutations.MutationsResponse
import com.synrgy7team4.feature_transfer.data.remote.response.transfer.TransferData
import com.synrgy7team4.feature_transfer.data.remote.response.transfer.TransferResponse
import com.synrgy7team4.feature_transfer.data.remote.response.transschedule.TransSchedData
import com.synrgy7team4.feature_transfer.data.remote.response.user.UserData
import com.synrgy7team4.feature_transfer.data.remote.response.user.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

class RemoteDataSource(private val apiService: ApiService) {

    fun postTransfer(token: String, request: TransferRequest): Flow<Resource<TransferResponse>> =
        flow {
            try {
                val response = apiService.postTransfer( token, request)
                if (response.success) {
                    Log.e("remote", "succ postTransfer: " + response.success)
                    Log.e("remote", "succ postTransfer: " + response.errors)
                    Log.e("remote", "succ postTransfer: " + response.message)
                    emit(Resource.Success(response))
                } else {
                    Log.e("RemoteDataSource", "Error postTransfer: " + response.errors.toString())
                    emit(Resource.Error(response.errors.toString()))
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = errorBody?.let {
                    // Assuming your API returns the error in a standard format
                    // You'll need a model class that matches the structure of your error response
                    Gson().fromJson(it, TransferResponse::class.java)
                }
                emit(Resource.Error(errorResponse?.errors ?: "HTTP ${e.code()}: Unknown error"))
            } catch (e: Exception) {
                Log.e("RemoteDataSource", "Error postTransfer: ${e.message}", e)
                emit(Resource.Error(e.message ?: "Unknown error"))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun postTransferSched(token: String, transSchedule: TransSchedule): TransSchedData {
        return apiService.postTransferSched(token, transSchedule)
    }

    suspend fun getUserData(token: String): UserResponse {
        return apiService.getUserData(token)
    }

    suspend fun getBalance(token: String, accountNumber: String): BalanceResponse {
        return apiService.getBalance(token, accountNumber)
    }

    suspend fun postBalance(token: String, balanceRequest: BalanceRequest): BalanceResponse {
        return apiService.postBalance(token, balanceRequest)
    }

    suspend fun getMutation(token: String, accountNumber: String): MutationsResponse {
        return apiService.getMutation(token, accountNumber)
    }

    suspend fun getAccountList(token: String): AccountResponse {
        return apiService.getAccountList(token)
    }

    suspend fun postAccount(token: String, accountRequest: AccountRequest): AccountResponse {
        return apiService.postAccount(token, accountRequest)
    }

    suspend fun cekAccount(token: String): List<Accounts> {
        return apiService.cekAccount(token)
    }
}


//class RemoteDataSource(private val apiService: ApiService) {
//
//    fun postTransfer(request: TransferRequest): Flow<Resource<TransferData>> =
//        flow {
//            try {
//                val response = apiService.postTransfer(request)
//                if (response.success) {
//                    emit(Resource.Success(response.data!!))
//                } else {
//                    emit(Resource.Error(response.message))
//                }
//            } catch (e: Exception) {
//                Log.e("RemoteDataSource", "Error postTransfer: ${e.message}", e)
//                emit(Resource.Error(e.message ?: "Unknown error"))
//            }
//        }.flowOn(Dispatchers.IO)
//
//
//    fun getBalance(accountNumber: String): Flow<Resource<Int>> =
//        flow {
//            try{
//                val response = apiService.getBalance(accountNumber)
//                if (response.success){
//                    emit(Resource.Success(response.data!!))
//                } else {
//                    emit((Resource.Error(response.message)))
//                }
//            } catch (e: Exception) {
//                Log.e("RemoteDataSource", "AccountError getBalance: ${e.message}", e)
//                emit(Resource.Error(e.message ?: "Unknown error"))
//            }
//    }.flowOn(Dispatchers.IO)
//
//
//    fun postBalance(request: BalanceRequest): Flow<Resource<Unit>> =
//        flow {
//            try {
//                val response = apiService.postBalance(request)
//                if(response.success) {
//                    emit(Resource.Success(Unit))
//                } else {
//                    emit((Resource.Error(response.message)))
//                }
//            }
//            catch (e: Exception) {
//                Log.e("RemoteDataSource", "AccountError postBalance: ${e.message}", e)
//                emit(Resource.Error(e.message ?: "Unknown error"))
//            }
//        }.flowOn(Dispatchers.IO)
//
//
//    fun postAccount(request: AccountRequest): Flow<Resource<Unit>> =
//        flow {
//            try {
//                val response = apiService.postAccount(request)
//                if(response.success) {
//                    emit(Resource.Success(Unit))
//                } else {
//                    emit((Resource.Error(response.message)))
//                }
//            }
//            catch (e: Exception) {
//                Log.e("RemoteDataSource", "Error postAccount: ${e.message}", e)
//                emit(Resource.Error(e.message ?: "Unknown error"))
//            }
//        }.flowOn(Dispatchers.IO)
//
//
//    fun getAccountList(): Flow<Resource<List<AccountData>>> =
//        flow {
//            try {
//                val response = apiService.getAccountList()
//                if(response.success){
//                    emit(Resource.Success(response.data!!))
//                }
//                else{
//                    emit(Resource.Error(response.message))
//                }
//            } catch (e: Exception){
//                Log.e("RemoteDataSource", "Error getAccountList: ${e.message}", e)
//                emit(Resource.Error(e.message ?: "Unknown error"))
//            }
//        }.flowOn(Dispatchers.IO)
//
//    fun getUserData(): Flow<Resource<UserData>> =
//        flow {
//            try {
//                val response = apiService.getUserData()
//                if(response.success){
//                    emit(Resource.Success(response.data!!))
//                }
//                else{
//                    emit(Resource.Error(response.message))
//                }
//            } catch (e: Exception){
//                Log.e("RemoteDataSource", "Error getUserData: ${e.message}", e)
//                emit(Resource.Error(e.message ?: "Unknown error"))
//            }
//        }.flowOn(Dispatchers.IO)
//}