package com.synrgy7team4.feature_auth.data.remote.retrofit

import android.util.Log
import com.synrgy7team4.feature_auth.data.remote.AuthRemoteSource
import com.synrgy7team4.feature_auth.domain.repository.AuthRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImplementAuthRemote (
    private val apiService: ApiService
) : AuthRemoteSource {
    override suspend fun register(name: String, email: String, password: String) {

        val registerRequest = RegisterBody(name, email, password)
        val call = apiService.register(registerRequest)
        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(p0: Call<RegisterResponse>, p1: Response<RegisterResponse>) {
                if (p1.isSuccessful) {
                    p1.body()
                } else {
                    Log.e("ImplementAuthRemote", "Failure: ${p1.message()}")
                }
            }

            override fun onFailure(p0: Call<RegisterResponse>, p1: Throwable) {
                Log.e("ImplementAuthRemote", "Failure: ${p1.message}")
            }

        })

    }

    override suspend fun login(email: String, password: String) {
        val loginRequest = LoginBody(email, password)
        val call = apiService.login(loginRequest)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(p0: Call<LoginResponse>, p1: Response<LoginResponse>) {
                if (p1.isSuccessful) {
                    p1.body()
                } else {
                    Log.e("ImplementAuthRemote", "Failure: ${p1.message()}")
                }
            }

            override fun onFailure(p0: Call<LoginResponse>, p1: Throwable) {
                Log.e("ImplementAuthRemote", "Failure: ${p1.message}")
            }

        })
    }


}