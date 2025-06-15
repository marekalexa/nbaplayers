package com.example.nbaplayers.data.remote.interceptor

import com.example.nbaplayers.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.request()
            .newBuilder()
            .addHeader("Authorization", BuildConfig.BALDONTLIE_TOKEN)
            .build()
            .let(chain::proceed)
}
