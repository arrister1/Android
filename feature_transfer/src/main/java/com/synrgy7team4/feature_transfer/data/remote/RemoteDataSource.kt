package com.synrgy7team4.feature_transfer.data.remote

import android.util.Log
import com.synrgy7team4.common.Resource
import com.synrgy7team4.feature_transfer.data.remote.network.ApiService
import com.synrgy7team4.feature_transfer.data.remote.request.AccountRequest
import com.synrgy7team4.feature_transfer.data.remote.request.BalanceRequest
import com.synrgy7team4.feature_transfer.data.remote.request.TransferRequest
import com.synrgy7team4.feature_transfer.data.remote.response.account.AccountData
import com.synrgy7team4.feature_transfer.data.remote.response.transfer.TransferData
import com.synrgy7team4.feature_transfer.data.remote.response.user.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    fun postTransfer(request: TransferRequest): Flow<Resource<TransferData>> =
        flow {
            try {
                val response = apiService.postTransfer(request)
                if (response.success) {
                    emit(Resource.Success(response.data!!))
                } else {
                    emit(Resource.Error(response.message))
                }
            } catch (e: Exception) {
                Log.e("RemoteDataSource", "Error postTransfer: ${e.message}", e)
                emit(Resource.Error(e.message ?: "Unknown error"))
            }
        }.flowOn(Dispatchers.IO)


    fun getBalance(accountNumber: String): Flow<Resource<Int>> =
        flow {
            try{
                val response = apiService.getBalance(accountNumber)
                if (response.success){
                    emit(Resource.Success(response.data!!))
                } else {
                    emit((Resource.Error(response.message)))
                }
            } catch (e: Exception) {
                Log.e("RemoteDataSource", "AccountError getBalance: ${e.message}", e)
                emit(Resource.Error(e.message ?: "Unknown error"))
            }
    }.flowOn(Dispatchers.IO)


    fun postBalance(request: BalanceRequest): Flow<Resource<Unit>> =
        flow {
            try {
                val response = apiService.postBalance(request)
                if(response.success) {
                    emit(Resource.Success(Unit))
                } else {
                    emit((Resource.Error(response.message)))
                }
            }
            catch (e: Exception) {
                Log.e("RemoteDataSource", "AccountError postBalance: ${e.message}", e)
                emit(Resource.Error(e.message ?: "Unknown error"))
            }
        }.flowOn(Dispatchers.IO)


    fun postAccount(request: AccountRequest): Flow<Resource<Unit>> =
        flow {
            try {
                val response = apiService.postAccount(request)
                if(response.success) {
                    emit(Resource.Success(Unit))
                } else {
                    emit((Resource.Error(response.message)))
                }
            }
            catch (e: Exception) {
                Log.e("RemoteDataSource", "Error postAccount: ${e.message}", e)
                emit(Resource.Error(e.message ?: "Unknown error"))
            }
        }.flowOn(Dispatchers.IO)


    fun getAccountList(): Flow<Resource<List<AccountData>>> =
        flow {
            try {
                val response = apiService.getAccountList()
                if(response.success){
                    emit(Resource.Success(response.data!!))
                }
                else{
                    emit(Resource.Error(response.message))
                }
            } catch (e: Exception){
                Log.e("RemoteDataSource", "Error getAccountList: ${e.message}", e)
                emit(Resource.Error(e.message ?: "Unknown error"))
            }
        }.flowOn(Dispatchers.IO)

    fun getUserData(): Flow<Resource<UserData>> =
        flow {
            try {
                val response = apiService.getUserData()
                if(response.success){
                    emit(Resource.Success(response.data!!))
                }
                else{
                    emit(Resource.Error(response.message))
                }
            } catch (e: Exception){
                Log.e("RemoteDataSource", "Error getUserData: ${e.message}", e)
                emit(Resource.Error(e.message ?: "Unknown error"))
            }
        }.flowOn(Dispatchers.IO)
}