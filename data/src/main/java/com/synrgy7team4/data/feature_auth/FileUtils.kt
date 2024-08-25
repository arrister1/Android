package com.synrgy7team4.data.feature_auth

import com.synrgy7team4.data.feature_auth.datasource.remote.response.EmailCheckResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.KtpNumberCheckResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.LoginData
import com.synrgy7team4.data.feature_auth.datasource.remote.response.LoginResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.OtpData
import com.synrgy7team4.data.feature_auth.datasource.remote.response.OtpResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.PhoneNumberCheckResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.RegisterData
import com.synrgy7team4.data.feature_auth.datasource.remote.response.RegisterResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.UserData
import com.synrgy7team4.data.feature_auth.datasource.remote.response.UserGetResponse
import com.synrgy7team4.domain.feature_auth.model.response.EmailCheckResponseDomain
import com.synrgy7team4.domain.feature_auth.model.response.KtpNumberCheckResponseDomain
import com.synrgy7team4.domain.feature_auth.model.response.LoginDataDomain
import com.synrgy7team4.domain.feature_auth.model.response.LoginResponseDomain
import com.synrgy7team4.domain.feature_auth.model.response.OtpDataDomain
import com.synrgy7team4.domain.feature_auth.model.response.OtpResponseDomain
import com.synrgy7team4.domain.feature_auth.model.response.PhoneNumberCheckResponseDomain
import com.synrgy7team4.domain.feature_auth.model.response.RegisterDataDomain
import com.synrgy7team4.domain.feature_auth.model.response.RegisterResponseDomain
import com.synrgy7team4.domain.feature_auth.model.response.UserDataDomain
import com.synrgy7team4.domain.feature_auth.model.response.UserGetResponseDomain

object FileUtils {
    fun LoginResponse.toLoginResponseDomain(): LoginResponseDomain =
        LoginResponseDomain(
            success = success,
            message = message,
            data = data.toLoginDataDomain()
        )

    private fun LoginData.toLoginDataDomain(): LoginDataDomain =
        LoginDataDomain(
            email = email ?: "",
            name = name ?: "",
            jwtToken = jwtToken ?: "",
            refreshToken = refreshToken ?: ""
        )

    fun RegisterResponse.toRegisterResponseDomain(): RegisterResponseDomain =
        RegisterResponseDomain(
            success = success,
            message = message,
            data = data.toRegisterDataDomain(),
        )

    private fun RegisterData.toRegisterDataDomain(): RegisterDataDomain =
        RegisterDataDomain(
            email = email,
            name = name,
            noHp = noHp,
            noKtp = noKtp,
            dateOfBirth = dateOfBirth,
            accountNumber = accountNumber,
            accountPin = accountPin,
            ektpPhoto = ektpPhoto
        )

    fun OtpResponse.toOtpResponseDomain(): OtpResponseDomain =
        OtpResponseDomain(
            success = success,
            data = data,
            message = message,
            errors = errors
        )

//    private fun OtpData.toOtpDataDomain(): OtpDataDomain =
//        OtpDataDomain(
//            email = email ?: "",
//            otp = otp ?: ""
//        )

    fun EmailCheckResponse.toEmailCheckResponseDomain(): EmailCheckResponseDomain =
        EmailCheckResponseDomain(
            success = success,
            data = data
        )

    fun PhoneNumberCheckResponse.toPhoneNumberCheckResponseDomain(): PhoneNumberCheckResponseDomain =
        PhoneNumberCheckResponseDomain(
            success = success,
            data = data
        )

    fun KtpNumberCheckResponse.toKtpNumberCheckResponseDomain(): KtpNumberCheckResponseDomain =
        KtpNumberCheckResponseDomain(
            success = success,
            data = data
        )

    fun UserGetResponse.toUserGetResponseDomain(): UserGetResponseDomain =
        UserGetResponseDomain(
            success = success,
            message = message,
            data = data.toUserDataDomain()
        )

    private fun UserData.toUserDataDomain(): UserDataDomain =
        UserDataDomain(
            accountNumber = accountNumber ?: "",
            noHp = noHp ?: "",
            dateOfBirth = dateOfBirth ?: "",
            accountPin = accountPin ?: "",
            noKtp = noKtp ?: "",
            name = name ?: "",
            ektpPhoto = ektpPhoto ?: "",
            email = email ?: ""
        )
}