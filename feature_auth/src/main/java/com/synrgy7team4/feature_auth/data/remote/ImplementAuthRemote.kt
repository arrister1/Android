package com.synrgy7team4.feature_auth.data.remote

import com.synrgy7team4.feature_auth.data.remote.response.Data
import com.synrgy7team4.feature_auth.data.remote.response.DataX
import com.synrgy7team4.feature_auth.data.remote.retrofit.ApiService
import com.synrgy7team4.feature_auth.data.remote.response.LoginResponse
import com.synrgy7team4.feature_auth.data.remote.response.RegisterResponse
import com.synrgy7team4.feature_auth.data.remote.retrofit.RegisterBody

class ImplementAuthRemote (
    private val apiService: ApiService
) : AuthRemoteSource {




//    override suspend fun register(name: String, email: String, password: String) {
//
//        val registerRequest = RegisterBody(name, email, password)
//        val call = apiService.register(registerRequest)
//        call.enqueue(object : Callback<RegisterResponse> {
//            override fun onResponse(p0: Call<RegisterResponse>, p1: Response<RegisterResponse>) {
//                if (p1.isSuccessful) {
//                    p1.body()
//                } else {
//                    Log.e("ImplementAuthRemote", "Failure: ${p1.message()}")
//                }
//            }
//
//            override fun onFailure(p0: Call<RegisterResponse>, p1: Throwable) {
//                Log.e("ImplementAuthRemote", "Failure: ${p1.message}")
//            }
//
//        })
//
//    }

    //    override suspend fun login(email: String, password: String) {
//        val loginRequest = LoginBody(email, password)
//        val call = apiService.login(loginRequest)
//        call.enqueue(object : Callback<LoginResponse> {
//            override fun onResponse(p0: Call<LoginResponse>, p1: Response<LoginResponse>) {
//                if (p1.isSuccessful) {
//                    p1.body()?.token
//                } else {
//                    Log.e("ImplementAuthRemote", "Failure: ${p1.message()}")
//                }
//            }
//
//            override fun onFailure(p0: Call<LoginResponse>, p1: Throwable) {
//                Log.e("ImplementAuthRemote", "Failure: ${p1.message}")
//            }
//
//        })
//    }
    override suspend fun register(registerBody: RegisterBody): Data {
        return apiService.register(registerBody)
    }

    override suspend fun login(email: String, password: String): DataX {
        return apiService.login(email, password)
    }


}