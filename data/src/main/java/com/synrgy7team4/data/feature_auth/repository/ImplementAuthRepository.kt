package com.synrgy7team4.data.feature_auth.repository

import com.synrgy7team4.data.feature_auth.datasource.remote.AuthRemoteDatasource
import com.synrgy7team4.data.feature_auth.FileUtils.toEmailCheckResponseDomain
import com.synrgy7team4.data.feature_auth.FileUtils.toKtpNumberCheckResponseDomain
import com.synrgy7team4.data.feature_auth.FileUtils.toLoginResponseDomain
import com.synrgy7team4.data.feature_auth.FileUtils.toPhoneNumberCheckResponseDomain
import com.synrgy7team4.data.feature_auth.FileUtils.toRegisterResponseDomain
import com.synrgy7team4.data.feature_auth.FileUtils.toOtpResponseDomain
import com.synrgy7team4.domain.feature_auth.model.request.EmailCheckRequest
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
import com.synrgy7team4.domain.feature_auth.repository.AuthRepository

class ImplementAuthRepository(
    private val authRemoteSource: AuthRemoteDatasource
) : AuthRepository {
    override suspend fun register(registerRequest: RegisterRequest): RegisterResponseDomain =
        authRemoteSource.register(registerRequest).toRegisterResponseDomain()

    override suspend fun sendOtp(sendOtpRequest: SendOtpRequest): OtpResponseDomain =
        authRemoteSource.sendOtp(sendOtpRequest).toOtpResponseDomain()


    override suspend fun verifyOtp(verifyOtpRequest: VerifyOtpRequest): OtpResponseDomain =
        authRemoteSource.verifyOtp(verifyOtpRequest).toOtpResponseDomain()


    override suspend fun login(loginRequest: LoginRequest): LoginResponseDomain =
        authRemoteSource.login(loginRequest).toLoginResponseDomain()

    override suspend fun checkEmailAvailability(emailCheckRequest: EmailCheckRequest): EmailCheckResponseDomain =
        authRemoteSource.checkEmailAvailability(emailCheckRequest).toEmailCheckResponseDomain()

    override suspend fun checkPhoneNumberAvailability(phoneNumberCheckRequest: PhoneNumberCheckRequest): PhoneNumberCheckResponseDomain =
        authRemoteSource.checkPhoneNumberAvailability(phoneNumberCheckRequest).toPhoneNumberCheckResponseDomain()

    override suspend fun checkKtpNumberAvailability(ktpNumberCheckRequest: KtpNumberCheckRequest): KtpNumberCheckResponseDomain =
        authRemoteSource.checkKtpNumberAvailability(ktpNumberCheckRequest).toKtpNumberCheckResponseDomain()
}