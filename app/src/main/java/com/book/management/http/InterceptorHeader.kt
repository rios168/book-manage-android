package com.book.management.http

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by Mark on 2022/9/6.
 */
class InterceptorHeader : Interceptor {

    private val token = "4E85975D8971023E5F8F650E9060F308"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder().header("Content-Type", "application/json;charset=UTF-8")
            .addHeader("token", token)

        val request = requestBuilder.build()

        return chain.proceed(request)
    }
}