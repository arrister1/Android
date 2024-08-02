package com.synrgy7team4.feature_auth.data.remote.retrofit

import com.synrgy7team4.feature_auth.data.Post
import com.synrgy7team4.feature_auth.data.remote.response.Data
import com.synrgy7team4.feature_auth.data.remote.response.DataX
import com.synrgy7team4.feature_auth.data.remote.response.LoginResponse
import com.synrgy7team4.feature_auth.data.remote.response.RegistResponse
import com.synrgy7team4.feature_auth.data.remote.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import java.util.Date

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

//    @Multipart
//    @POST("auth/register")
//    suspend fun register(
//        @PartMap partMap: Map<String, @JvmSuppressWildcards RequestBody>,
//        @Part photo: MultipartBody.Part
//    ): Data



//
    @POST("auth/register")
    suspend fun register(
       @Body registerBody: RegisterBody
    ): RegistResponse
//        Data
//            RegisterResponse

    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): DataX
//            LoginResponse



//    @POST("register")
//    suspend fun register(
//        @Body registerBody: RegisterBody
//    ): Call<RegisterResponse>
//
//    @POST("login")
//    suspend fun login(
//        @Body loginBody: LoginBody
//    ): Call<LoginResponse>
}