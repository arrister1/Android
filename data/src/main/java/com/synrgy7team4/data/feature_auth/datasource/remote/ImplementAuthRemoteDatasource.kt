package com.synrgy7team4.data.feature_auth.datasource.remote

import android.util.Log
import com.synrgy7team4.data.feature_auth.datasource.remote.response.LoginErrorResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.LoginResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.RegisterResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.retrofit.ApiService
import com.synrgy7team4.domain.feature_auth.model.request.RegisterRequest
import com.synrgy7team4.domain.feature_auth.usecase.HttpExceptionUseCase
import com.google.gson.Gson
import com.synrgy7team4.data.feature_auth.datasource.remote.response.EmailCheckErrorResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.EmailCheckResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.KtpNumberCheckErrorResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.KtpNumberCheckResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.OtpErrorResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.OtpResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.PhoneNumberCheckErrorResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.PhoneNumberCheckResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.RegisterErrorResponse
import com.synrgy7team4.domain.feature_auth.model.request.EmailCheckRequest
import com.synrgy7team4.domain.feature_auth.model.request.KtpNumberCheckRequest
import com.synrgy7team4.domain.feature_auth.model.request.LoginRequest
import com.synrgy7team4.domain.feature_auth.model.request.PhoneNumberCheckRequest
import com.synrgy7team4.domain.feature_auth.model.request.SendOtpRequest
import com.synrgy7team4.domain.feature_auth.model.request.VerifyOtpRequest

import retrofit2.HttpException

class ImplementAuthRemoteDatasource(
    private val apiService: ApiService
) : AuthRemoteDatasource {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse =
        try {
            apiService.login(loginRequest)
        } catch (e: HttpException) {
            val json = e.response()?.errorBody()?.string()
            val error = Gson().fromJson(json, LoginErrorResponse::class.java)
            throw HttpExceptionUseCase(e, error.errors)
        }

    override suspend fun register(registerRequest: RegisterRequest): RegisterResponse =
        try {
            val response = apiService.register(registerRequest)
            Log.d("RegisterResponse", "Response JSON: $response,  ${response.message}")
            response

        } catch (e: HttpException) {


            val jsonMessage = e.message
            val json = e.response()?.errorBody()?.string()
            Log.e("RegisterError", "Response JSON: $json")
//            val error = Gson().fromJson(json, RegisterErrorResponse::class.java)
            val error = json?.let { Gson().fromJson(it, RegisterErrorResponse::class.java) }
            val errorMessage = error?.errors
//            Log.e("RegisterError", "Response JSON: ${error.errors}")
            throw HttpExceptionUseCase(e, errorMessage)
        }

    override suspend fun sendOtp(sendOtpRequest: SendOtpRequest): OtpResponse =
        try {
            apiService.sendOtp(sendOtpRequest)
        } catch (e: HttpException) {
            val json = e.response()?.errorBody()?.string()
            val error = Gson().fromJson(json, OtpErrorResponse::class.java)
            throw HttpExceptionUseCase(e, error.message)
        }

    override suspend fun verifyOtp(verifyOtpRequest: VerifyOtpRequest): OtpResponse =
        try {
            apiService.verifyOtp(verifyOtpRequest)
        } catch (e: HttpException) {
            val json = e.response()?.errorBody()?.string()
            val error = Gson().fromJson(json, OtpErrorResponse::class.java)
            throw HttpExceptionUseCase(e, error.message)
        }

    override suspend fun checkEmailAvailability(email: EmailCheckRequest): EmailCheckResponse =
        try {
            apiService.checkEmailAvailability(email)
        } catch (e: HttpException) {
            val json = e.response()?.errorBody()?.string()
            val error = Gson().fromJson(json, EmailCheckErrorResponse::class.java)
            throw HttpExceptionUseCase(e, error.errors)
        }

    override suspend fun checkPhoneNumberAvailability(phoneNumber: PhoneNumberCheckRequest): PhoneNumberCheckResponse =
        try {
            apiService.checkPhoneNumberAvailability(phoneNumber)
        } catch (e: HttpException) {
            val json = e.response()?.errorBody()?.string()
            val error = Gson().fromJson(json, PhoneNumberCheckErrorResponse::class.java)
            throw HttpExceptionUseCase(e, error.errors)
        }

    override suspend fun checkKtpNumberAvailability(ktpNumber: KtpNumberCheckRequest): KtpNumberCheckResponse =
        try {
            apiService.checkKtpNumberAvailability(ktpNumber)
        } catch (e: HttpException) {
            val json = e.response()?.errorBody()?.string()
            val error = Gson().fromJson(json, KtpNumberCheckErrorResponse::class.java)
            throw HttpExceptionUseCase(e, error.errors)
        }
}