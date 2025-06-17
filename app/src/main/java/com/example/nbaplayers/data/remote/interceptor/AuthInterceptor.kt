package com.example.nbaplayers.data.remote.interceptor

import com.example.nbaplayers.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * OkHttp interceptor that adds authentication headers to API requests.
 */
class AuthInterceptor @Inject constructor() : Interceptor {

    /**
     * Intercepts the request and adds necessary authentication headers.
     *
     * @param chain The interceptor chain
     * @return The response with added headers
     */
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.request()
            .newBuilder()
            .addHeader("Authorization", BuildConfig.BALDONTLIE_TOKEN)
            .build()
            .let(chain::proceed)
}
