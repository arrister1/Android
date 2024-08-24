package com.synrgy7team4.feature_mutasi.data.remote


class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getPosts() = apiService.getPosts()

    suspend fun getMutationsByAcc(query: String, token:String) = apiService.getMutationsByAcc(accountNumber = query, token = token)

    suspend fun getUserData(token:String) = apiService.getUserData(token)
}