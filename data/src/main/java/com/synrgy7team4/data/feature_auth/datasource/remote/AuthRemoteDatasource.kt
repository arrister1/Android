package com.synrgy7team4.data.feature_auth.datasource.remote

import com.synrgy7team4.data.feature_auth.datasource.remote.response.EmailCheckResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.KtpNumberCheckResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.LoginResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.OtpResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.PhoneNumberCheckResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.RegisterResponse
import com.synrgy7team4.domain.feature_auth.model.request.EmailCheckRequest
import com.synrgy7team4.domain.feature_auth.model.request.ForgotPasswordRequest
import com.synrgy7team4.domain.feature_auth.model.request.KtpNumberCheckRequest
import com.synrgy7team4.domain.feature_auth.model.request.LoginRequest
import com.synrgy7team4.domain.feature_auth.model.request.PhoneNumberCheckRequest
import com.synrgy7team4.domain.feature_auth.model.request.RegisterRequest
import com.synrgy7team4.domain.feature_auth.model.request.SendOtpRequest
import com.synrgy7team4.domain.feature_auth.model.request.VerifyOtpRequest
import com.synrgy7team4.feature_auth.data.remote.response.ForgotPasswordResponse


interface AuthRemoteDatasource {
    suspend fun login(loginRequest: LoginRequest): LoginResponse
    suspend fun register(registerRequest: RegisterRequest): RegisterResponse
    suspend fun sendOtp(sendOtpRequest: SendOtpRequest): OtpResponse
    suspend fun verifyOtp(verifyOtpRequest: VerifyOtpRequest): OtpResponse
    suspend fun checkEmailAvailability(email: EmailCheckRequest): EmailCheckResponse
    suspend fun checkPhoneNumberAvailability(phoneNumber: PhoneNumberCheckRequest): PhoneNumberCheckResponse
    suspend fun checkKtpNumberAvailability(ktpNumber: KtpNumberCheckRequest): KtpNumberCheckResponse
    suspend fun sendForgetPass(sendForgetPass: ForgotPasswordRequest): ForgotPasswordResponse
    suspend fun validateForgetPass(validateForgetPass: ForgotPasswordRequest): ForgotPasswordResponse
    suspend fun setNewPass(setNewPass: ForgotPasswordRequest): ForgotPasswordResponse

}