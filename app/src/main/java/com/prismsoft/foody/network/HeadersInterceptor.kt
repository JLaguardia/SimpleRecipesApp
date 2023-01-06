package com.prismsoft.foody.network

import okhttp3.Interceptor
import okhttp3.Response

class HeadersInterceptor(private val apiKey: String): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()
            .newBuilder()
            .header("x-api-key", apiKey)
            .build()
        return chain.proceed(req)
    }
}