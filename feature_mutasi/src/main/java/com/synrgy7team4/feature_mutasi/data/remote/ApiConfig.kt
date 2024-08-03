package com.synrgy7team4.feature_mutasi.data.remote

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.synrgy7team4.common.Constants.Companion.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {
    fun provideApiService(context: Context): ApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(provideOkhttpClient(context))
            .build()
            .create(ApiService::class.java)

    private fun provideOkhttpClient(context: Context): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(provideHttpLoggingInterceptor())
            .addInterceptor(provideChuckerInterceptor(context))
            .build()

    private fun provideHttpLoggingInterceptor(): Interceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    private fun provideChuckerInterceptor(context: Context): Interceptor =
        ChuckerInterceptor.Builder(context).build()
}