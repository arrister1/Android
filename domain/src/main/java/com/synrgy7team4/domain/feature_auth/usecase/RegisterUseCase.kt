package com.synrgy7team4.domain.feature_auth.usecase

import com.synrgy7team4.domain.feature_auth.model.request.EmailCheckRequest
import com.synrgy7team4.domain.feature_auth.model.request.ForgotPasswordRequest
import com.synrgy7team4.domain.feature_auth.model.request.KtpNumberCheckRequest
import com.synrgy7team4.domain.feature_auth.model.request.PhoneNumberCheckRequest
import com.synrgy7team4.domain.feature_auth.model.request.RegisterRequest
import com.synrgy7team4.domain.feature_auth.model.request.SendOtpRequest
import com.synrgy7team4.domain.feature_auth.model.request.VerifyOtpRequest
import com.synrgy7team4.domain.feature_auth.model.response.EmailCheckResponseDomain
import com.synrgy7team4.domain.feature_auth.model.response.KtpNumberCheckResponseDomain
import com.synrgy7team4.domain.feature_auth.model.response.OtpResponseDomain
import com.synrgy7team4.domain.feature_auth.model.response.PhoneNumberCheckResponseDomain
import com.synrgy7team4.domain.feature_auth.model.response.RegisterResponseDomain
import com.synrgy7team4.domain.feature_auth.repository.AuthRepository
import com.synrgy7team4.feature_auth.data.remote.response.ForgotPasswordResponseDomain

class RegisterUseCase(
    private val authRepository: AuthRepository
) {
    suspend fun register(registerRequest: RegisterRequest): RegisterResponseDomain =
        authRepository.register(registerRequest)

    suspend fun sendOtp(sendOtpRequest: SendOtpRequest): OtpResponseDomain =
        authRepository.sendOtp(sendOtpRequest)

    suspend fun verifyOtp(verifyOtpRequest: VerifyOtpRequest): OtpResponseDomain =
        authRepository.verifyOtp(verifyOtpRequest)

    suspend fun checkEmailAvailability(emailCheckRequest: EmailCheckRequest): EmailCheckResponseDomain =
        authRepository.checkEmailAvailability(emailCheckRequest)

    suspend fun checkPhoneNumberAvailability(phoneNumberCheckRequest: PhoneNumberCheckRequest): PhoneNumberCheckResponseDomain =
        authRepository.checkPhoneNumberAvailability(phoneNumberCheckRequest)

    suspend fun checkKtpNumberAvailability(ktpNumberCheckRequest: KtpNumberCheckRequest): KtpNumberCheckResponseDomain =
        authRepository.checkKtpNumberAvailability(ktpNumberCheckRequest)

    suspend fun sendForgetPass(sendForgetPass: ForgotPasswordRequest): ForgotPasswordResponseDomain =
        authRepository.sendForgetPass(sendForgetPass)

    suspend fun validateForgetPass(validateForgetPass: ForgotPasswordRequest): ForgotPasswordResponseDomain =
        authRepository.validateForgetPass(validateForgetPass)

    suspend fun setNewPass(setNewPass: ForgotPasswordRequest): ForgotPasswordResponseDomain =
        authRepository.setNewPass(setNewPass)

}
