package com.synrgy7team4.feature_auth.data.remote

import android.content.Context
import android.net.Uri
import com.synrgy7team4.feature_auth.data.remote.response.Data
import com.synrgy7team4.feature_auth.data.remote.response.DataX
import com.synrgy7team4.feature_auth.data.remote.response.LoginResponse
import com.synrgy7team4.feature_auth.data.remote.response.RegisterResponse
import com.synrgy7team4.feature_auth.data.remote.retrofit.RegisterBody

interface AuthRemoteSource {

//    suspend fun register(name: String, email: String, password: String): RegisterResponse
//    suspend fun register(registerBody: RegisterBody): Data
    suspend fun register(registerBody: RegisterBody, context: Context, uri: Uri): Data

//    suspend fun login(email: String, password: String): LoginResponse
    suspend fun login(email: String, password: String): DataX

}