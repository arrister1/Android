package com.synrgy7team4.feature_transfer.data.remote.repository

import android.util.Log
import com.synrgy7team4.common.Resource
import com.synrgy7team4.feature_transfer.data.remote.RemoteDataSource
import com.synrgy7team4.feature_transfer.data.remote.request.AccountRequest
import com.synrgy7team4.feature_transfer.data.remote.request.BalanceRequest
import com.synrgy7team4.feature_transfer.data.remote.request.TransSchedule
import com.synrgy7team4.feature_transfer.data.remote.request.TransferRequest
import com.synrgy7team4.feature_transfer.data.remote.response.account.AccountResponse
import com.synrgy7team4.feature_transfer.data.remote.response.account.Accounts
import com.synrgy7team4.feature_transfer.data.remote.response.account.toDomain
import com.synrgy7team4.feature_transfer.data.remote.response.balance.BalanceResponse
import com.synrgy7team4.feature_transfer.data.remote.response.mutations.Data

import com.synrgy7team4.feature_transfer.data.remote.response.mutations.MutationsResponse
import com.synrgy7team4.feature_transfer.data.remote.response.transfer.TransferResponse
import com.synrgy7team4.feature_transfer.data.remote.response.transfer.toDomain
import com.synrgy7team4.feature_transfer.data.remote.response.transschedule.TransSchedData
import com.synrgy7team4.feature_transfer.data.remote.response.user.UserResponse
import com.synrgy7team4.feature_transfer.data.remote.response.user.toDomain
import com.synrgy7team4.feature_transfer.domain.model.Account
import com.synrgy7team4.feature_transfer.domain.model.AccountReq
import com.synrgy7team4.feature_transfer.domain.model.BalanceReq
import com.synrgy7team4.feature_transfer.domain.model.Transfer
import com.synrgy7team4.feature_transfer.domain.model.TransferReq
import com.synrgy7team4.feature_transfer.domain.model.TransferRes
import com.synrgy7team4.feature_transfer.domain.model.User
import com.synrgy7team4.feature_transfer.domain.repository.TransferRepository
import com.synrgy7team4.feature_transfer.domain.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class TransferRepositoryImpl(private val remoteDataSource: RemoteDataSource): TransferRepository {
    override fun postTransfer(token: String,request: TransferReq): Flow<Resource<TransferRes>> {
        return remoteDataSource.postTransfer(token, request.toEntity())
            .map { resource ->
                when(resource) {
                    is Resource.Success -> {
                        val domainData = resource.data?.toDomain()
                        if (domainData != null) {
                            Log.e("repoimpl", "succ postTransfer: " + resource.data?.success)
                            Log.e("repoimpl", "succ postTransfer: " + resource.data?.errors)
                            Log.e("repoimpl", "succ postTransfer: " + resource.data?.message)
                            Resource.Success(domainData)
                        } else {
                            Log.e("repoimpl", "Error postTransfer: " + resource.data?.errors.toString())
                            Resource.Error(resource.data?.errors ?: "Unknown error on postTransfer")
                        }
                    }

                    is Resource.Error -> Resource.Error(resource.message ?: "Unknown error on postTransfer")
                    is Resource.Loading -> Resource.Loading()
                }
            }
    }

    override suspend fun postTransferSched(token: String, transScheduleRequest: TransSchedule): TransSchedData {
        return remoteDataSource.postTransferSched(token, transScheduleRequest)
    }

    override suspend fun getUserData(token: String): UserResponse {
        return remoteDataSource.getUserData(token)
    }

    override suspend fun getBalance(token: String, accountNumber: String): BalanceResponse {
        return remoteDataSource.getBalance(token, accountNumber)
    }

    override suspend fun postBalance(token: String, balanceRequest: BalanceRequest): BalanceResponse {
        return remoteDataSource.postBalance(token, balanceRequest)
    }

    override suspend fun getMutation(token: String, accountNumber: String): MutationsResponse {
        return remoteDataSource.getMutation(token, accountNumber)
    }

    override suspend fun getAccountList(token: String): AccountResponse {
        return remoteDataSource.getAccountList(token)
    }

    override suspend fun postAccount(token: String, accountRequest: AccountRequest): AccountResponse {
        return remoteDataSource.postAccount(token, accountRequest)
    }

    override suspend fun cekAccount(token: String): List<Accounts> {
        return remoteDataSource.cekAccount(token)
    }
}

//    override fun postTransfer(request: TransferReq): Flow<Resource<Transfer>> {
//        return remoteDataSource.postTransfer(request.toEntity())
//            .map { resource ->
//                when(resource) {
//                    is Resource.Success -> {
//                        val domainData = resource.data?.toDomain()
//                        if (domainData != null) {
//                            Resource.Success(domainData)
//                        } else {
//                            Resource.Error("Trasnfer Data is null")
//                        }
//                    }
//
//                    is Resource.Error -> Resource.Error(resource.message ?: "Unknown error on postTransfer")
//                    is Resource.Loading -> Resource.Loading()
//                }
//            }    }
//
//    //PAKE EMIT
////    override fun postTransfer(request: TransferReq): Flow<Resource<Transfer>> {
////        return flow {
////            emit(Resource.Loading()) // Emit loading state pertama kali
////            remoteDataSource.postTransfer(request.toEntity()).map { resource ->
////                when (resource) {
////                    is Resource.Success -> {
////                        // Memastikan data yang dikembalikan tidak null sebelum diproses lebih lanjut
////                        val domainData = resource.data?.toDomain()
////                        if (domainData != null) {
////                            Resource.Success(domainData)
////                        } else {
////                            Resource.Error("Data is null")
////                        }
////                    }
////                    is Resource.Error -> Resource.Error(resource.message ?: "Unknown error")
////                    is Resource.Loading -> Resource.Loading()
////                }
////            }.collect { resource ->
////                emit(resource) // Emit hasil resource setelah diproses
////            }
////        }.catch { e ->
////            // Menangani exception yang mungkin terjadi dan mengubahnya menjadi Resource.Error
////            emit(Resource.Error(e.message ?: "An unknown error occurred"))
////        }.flowOn(Dispatchers.IO) // Menggunakan Dispatchers.IO untuk operasi jaringan
////    }
//
//
//    override fun getBalance(accountNumber: String): Flow<Resource<Int>> {
//        return remoteDataSource.getBalance(accountNumber)
//            .map { resource ->
//                when (resource) {
//                    is Resource.Success -> {
//                        val balance = resource.data
//                        if(balance != null) {
//                            Resource.Success(balance)
//                        } else {
//                            Resource.Error("Balance is null")
//                        }
//                    }
//                    is Resource.Error -> Resource.Error(resource.message ?: "Unknown error on tetBalance")
//                    is Resource.Loading -> Resource.Loading()
//                }
//            }
//    }
//
//    override fun postBalance(request: BalanceReq): Flow<Resource<Unit>> {
//        return remoteDataSource.postBalance(request.toEntity())
//            .map { resource ->
//                when(resource){
//                    is Resource.Success ->{
//                     Resource.Success(Unit)
//                    }
//                    is Resource.Error -> Resource.Error(resource.message ?: "Unknown error on PostBalance")
//                    is Resource.Loading -> Resource.Loading()                }
//            }
//    }
//
//    //EMIT
////    override fun getBalance(accountNumber: String): Flow<Resource<Int>> {
////        return flow {
////            emit(Resource.Loading()) // Emit loading state pertama kali
////            remoteDataSource.getBalance(accountNumber).map { resource ->
////                when (resource) {
////                    is Resource.Success -> {
////                        // Mengembalikan data langsung jika tidak null
////                        val balance = resource.data
////                        if (balance != null) {
////                            Resource.Success(balance)
////                        } else {
////                            Resource.Error("Data is null")
////                        }
////                    }
////                    is Resource.Error -> Resource.Error(resource.message ?: "Unknown error")
////                    is Resource.Loading -> Resource.Loading()
////                }
////            }.collect { resource ->
////                emit(resource) // Emit hasil resource setelah diproses
////            }
////        }.catch { e ->
////            // Menangani exception yang mungkin terjadi dan mengubahnya menjadi Resource.Error
////            emit(Resource.Error(e.message ?: "An unknown error occurred"))
////        }.flowOn(Dispatchers.IO) // Menggunakan Dispatchers.IO untuk operasi jaringan
////    }
//
//    override fun getAccountList(): Flow<Resource<List<Account>>> {
//        return remoteDataSource.getAccountList()
//            .map { resource ->
//                when(resource) {
//                    is Resource.Success -> {
//                        val domAccount = resource.data?.map{it.toDomain()}
//                        if(domAccount != null) {
//                            Resource.Success(domAccount)
//                        } else {
//                            Resource.Error("UserData is null")
//                        }
//                    }
//                    is Resource.Error -> Resource.Error(resource.message ?: "Unknown error on getUserData")
//                    is Resource.Loading -> Resource.Loading()
//                }
//            }    }
//
//    //EMIT
////    override fun getAccountList(): Flow<Resource<List<Account>>> {
////        return flow {
////            emit(Resource.Loading()) // Emit loading state pertama kali
////            remoteDataSource.getAccountList().map { resource ->
////                when (resource) {
////                    is Resource.Success -> {
////                        // Mengonversi list data ke domain model jika data tidak null
////                        val accountList = resource.data?.map { it.toDomain() }
////                        if (accountList != null) {
////                            Resource.Success(accountList)
////                        } else {
////                            Resource.Error("Data is null")
////                        }
////                    }
////                    is Resource.Error -> Resource.Error(resource.message ?: "Unknown error")
////                    is Resource.Loading -> Resource.Loading()
////                }
////            }.collect { resource ->
////                emit(resource) // Emit hasil resource setelah diproses
////            }
////        }.catch { e ->
////            // Menangani exception yang mungkin terjadi dan mengubahnya menjadi Resource.Error
////            emit(Resource.Error(e.message ?: "An unknown error occurred"))
////        }.flowOn(Dispatchers.IO) // Menggunakan Dispatchers.IO untuk operasi jaringan
////    }
//
//    override fun postAccountList(request: AccountReq): Flow<Resource<Unit>> {
//        return remoteDataSource.postAccount(request.toEntity())
//            .map { resource ->
//                when(resource){
//                    is Resource.Success ->{
//                        Resource.Success(Unit)
//                    }
//                    is Resource.Error -> Resource.Error(resource.message ?: "Unknown error on postAccount")
//                    is Resource.Loading -> Resource.Loading()                }
//            }    }
//
//
//    //EMIT
////    override fun postAccountList(request: AccountReq): Flow<Resource<Unit>> {
////        return flow {
////            emit(Resource.Loading()) // Emit loading state pertama kali
////            remoteDataSource.postAccount(request.toEntity()).map { resource ->
////                when (resource) {
////                    is Resource.Success -> {
////                        // Tidak ada data tambahan yang perlu diproses dalam kasus ini, karena Resource.Success hanya membutuhkan Unit
////                        Resource.Success(Unit)
////                    }
////                    is Resource.Error -> Resource.Error(resource.message ?: "Unknown error")
////                    is Resource.Loading -> Resource.Loading()
////                }
////            }.collect { resource ->
////                emit(resource) // Emit hasil resource setelah diproses
////            }
////        }.catch { e ->
////            // Menangani exception yang mungkin terjadi dan mengubahnya menjadi Resource.Error
////            emit(Resource.Error(e.message ?: "An unknown error occurred"))
////        }.flowOn(Dispatchers.IO) // Menggunakan Dispatchers.IO untuk operasi jaringan
////    }
//
//
//    override fun getUserData(): Flow<Resource<User>> {
//        return remoteDataSource.getUserData()
//            .map { resource ->
//                when(resource) {
//                    is Resource.Success -> {
//                        val domUser = resource.data?.toDomain()
//                        if(domUser != null) {
//                            Resource.Success(domUser)
//                        } else {
//                            Resource.Error("UserData is null")
//                        }
//                    }
//                    is Resource.Error -> Resource.Error(resource.message ?: "Unknown error on getUserData")
//                    is Resource.Loading -> Resource.Loading()
//                }
//            }
//    }
//
//    //EMIT
////    override fun getUserData(): Flow<Resource<User>> {
////        return flow {
////            emit(Resource.Loading()) // Emit loading state pertama kali
////            remoteDataSource.getUserData().map { resource ->
////                when (resource) {
////                    is Resource.Success -> {
////                        // Mengonversi data ke domain model jika data tidak null
////                        val domainData = resource.data?.toDomain()
////                        if (domainData != null) {
////                            Resource.Success(domainData)
////                        } else {
////                            Resource.Error("Data is null")
////                        }
////                    }
////                    is Resource.Error -> Resource.Error(resource.message ?: "Unknown error")
////                    is Resource.Loading -> Resource.Loading()
////                }
////            }.collect { resource ->
////                emit(resource) // Emit hasil resource setelah diproses
////            }
////        }.catch { e ->
////            // Menangani exception yang mungkin terjadi dan mengubahnya menjadi Resource.Error
////            emit(Resource.Error(e.message ?: "An unknown error occurred"))
////        }.flowOn(Dispatchers.IO) // Menggunakan Dispatchers.IO untuk operasi jaringan
////    }
//
//}