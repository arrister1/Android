package com.synrgy7team4.data.feature_auth.utils

import com.synrgy7team4.data.feature_auth.datasource.remote.response.LoginData
import com.synrgy7team4.data.feature_auth.datasource.remote.response.LoginResponse
import com.synrgy7team4.data.feature_auth.datasource.remote.response.RegisterData
import com.synrgy7team4.data.feature_auth.datasource.remote.response.RegisterResponse
import com.synrgy7team4.domain.feature_auth.model.response.LoginDataDomain
import com.synrgy7team4.domain.feature_auth.model.response.LoginResponseDomain
import com.synrgy7team4.domain.feature_auth.model.response.RegisterDataDomain
import com.synrgy7team4.domain.feature_auth.model.response.RegisterResponseDomain

object FileUtils {
    fun LoginResponse.toLoginResponseDomain(): LoginResponseDomain =
        LoginResponseDomain(
            success = success,
            message = message,
            data = data.toLoginDataDomain()
        )

    private fun LoginData.toLoginDataDomain(): LoginDataDomain =
        LoginDataDomain(
            email = email,
            name = name,
            jwtToken = jwtToken,
            refreshToken = refreshToken
        )

    fun RegisterResponse.toRegisterResponseDomain(): RegisterResponseDomain =
        RegisterResponseDomain(
            success = success,
            message = message,
            data = data.toRegisterDataDomain()
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
}