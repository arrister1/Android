package com.synrgy7team4.feature_dashboard.data.remote

import com.synrgy7team4.feature_dashboard.data.Post
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>
}