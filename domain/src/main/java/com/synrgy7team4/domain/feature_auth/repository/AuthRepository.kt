package com.synrgy7team4.domain.feature_auth.repository

import com.synrgy7team4.domain.feature_auth.model.request.EmailCheckRequest
import com.synrgy7team4.domain.feature_auth.model.request.ForgotPasswordRequest
import com.synrgy7team4.domain.feature_auth.model.request.KtpNumberCheckRequest
import com.synrgy7team4.domain.feature_auth.model.request.LoginRequest
import com.synrgy7team4.domain.feature_auth.model.request.PhoneNumberCheckRequest
import com.synrgy7team4.domain.feature_auth.model.request.RegisterRequest
import com.synrgy7team4.domain.feature_auth.model.request.SendOtpRequest
import com.synrgy7team4.domain.feature_auth.model.request.VerifyOtpRequest
import com.synrgy7team4.domain.feature_auth.model.response.EmailCheckResponseDomain
import com.synrgy7team4.domain.feature_auth.model.response.KtpNumberCheckResponseDomain
import com.synrgy7team4.domain.feature_auth.model.response.LoginResponseDomain
import com.synrgy7team4.domain.feature_auth.model.response.OtpResponseDomain
import com.synrgy7team4.domain.feature_auth.model.response.PhoneNumberCheckResponseDomain
import com.synrgy7team4.domain.feature_auth.model.response.RegisterResponseDomain
import com.synrgy7team4.feature_auth.data.remote.response.ForgotPasswordResponseDomain

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest): LoginResponseDomain
    suspend fun register(registerRequest: RegisterRequest): RegisterResponseDomain
    suspend fun sendOtp(sendOtpRequest: SendOtpRequest): OtpResponseDomain
    suspend fun verifyOtp(verifyOtpRequest: VerifyOtpRequest): OtpResponseDomain
    suspend fun checkEmailAvailability(emailCheckRequest: EmailCheckRequest): EmailCheckResponseDomain
    suspend fun checkPhoneNumberAvailability(phoneNumberCheckRequest: PhoneNumberCheckRequest): PhoneNumberCheckResponseDomain
    suspend fun checkKtpNumberAvailability(ktpNumberCheckRequest: KtpNumberCheckRequest): KtpNumberCheckResponseDomain
    suspend fun sendForgetPass(sendForgetPass: ForgotPasswordRequest): ForgotPasswordResponseDomain
    suspend fun validateForgetPass(validateForgetPass: ForgotPasswordRequest): ForgotPasswordResponseDomain
    suspend fun setNewPass(setNewPass: ForgotPasswordRequest): ForgotPasswordResponseDomain

}