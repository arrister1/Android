package com.synrgy7team4.domain.feature_dashboard.usecase

class HttpExceptionUseCase(
    cause: Throwable? = null,
    message: String? = null,
) : Exception(message, cause)