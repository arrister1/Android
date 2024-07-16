package com.synrgy7team4.feature_mutasi.data.remote

import com.synrgy7team4.feature_mutasi.data.Post
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>
}