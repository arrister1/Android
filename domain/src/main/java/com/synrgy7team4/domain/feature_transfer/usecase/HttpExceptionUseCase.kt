package com.synrgy7team4.domain.feature_transfer.usecase

class HttpExceptionUseCase(
    cause: Throwable? = null,
    message: String? = null,
) : Exception(message, cause)