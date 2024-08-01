package com.synrgy7team4.feature_auth.data.remote

import android.content.Context
import android.net.Uri
import com.synrgy7team4.feature_auth.data.Utils.FileUtils
import com.synrgy7team4.feature_auth.data.remote.response.Data
import com.synrgy7team4.feature_auth.data.remote.response.DataX
import com.synrgy7team4.feature_auth.data.remote.retrofit.ApiService
import com.synrgy7team4.feature_auth.data.remote.response.LoginResponse
import com.synrgy7team4.feature_auth.data.remote.response.RegisterResponse
import com.synrgy7team4.feature_auth.data.remote.retrofit.RegisterBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ImplementAuthRemote (
    private val apiService: ApiService
) : AuthRemoteSource {



    override suspend fun register(registerBody: RegisterBody): Data {
        return apiService.register(registerBody)
    }


    override suspend fun login(email: String, password: String): DataX {
        return apiService.login(email, password)
    }


// Ga dipake cuk
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

//    Ga di pake cuk
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

//    private fun prepareFilePart(partName: String, fileUri: Uri, context: Context): MultipartBody.Part {
//        val file = FileUtils.getFile(context, fileUri)
//        val requestFile = RequestBody.create(context.contentResolver.getType(fileUri)?.toMediaTypeOrNull(), file)
//        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
//    }
//
//    private fun createPartFromString(value: String): RequestBody {
//        return RequestBody.create("multipart/form-data".toMediaTypeOrNull(),value)
//    }

//    override suspend fun register(registerBody: RegisterBody, context: Context, uri: Uri): Data {
////        return apiService.register(registerBody)
//        val body = prepareFilePart("photo", uri, context)
//        val map = hashMapOf(
//            "email" to createPartFromString(registerBody.email),
//            "no_hp" to createPartFromString(registerBody.no_hp),
//            "password" to createPartFromString(registerBody.password),
//            "confirm_password" to createPartFromString(registerBody.confirm_password),
//            "no_ktp" to createPartFromString(registerBody.no_ktp),
//            "name" to createPartFromString(registerBody.name),
//            "date_of_birth" to createPartFromString(registerBody.date_of_birth),
//            "pin" to createPartFromString(registerBody.pin),
//            "confirm_pin" to createPartFromString(registerBody.confirm_pin),
//            "ektp_photo" to createPartFromString(registerBody.ektp_photo),
//        )
//
//        return apiService.register(map, body)
//
//    }




}