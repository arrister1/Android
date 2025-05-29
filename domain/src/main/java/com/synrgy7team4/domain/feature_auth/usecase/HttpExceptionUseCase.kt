package com.synrgy7team4.domain.feature_auth.usecase

class HttpExceptionUseCase(
    cause: Throwable? = null,
//    message: String? = null,
    errors: String? = null,
) : Exception(
//    message ?:
    errors, cause)