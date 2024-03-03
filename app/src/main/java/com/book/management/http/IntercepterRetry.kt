package com.book.management.http

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by Mark on 2022/8/26.
 * 重试拦截器
 */
class IntercepterRetry(val maxRetry: Int = 2, val delayMills: Long = 1000) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var retryNum = 0 //假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）
        val request = chain.request()
        var response = chain.proceed(request)
        while (!response.isSuccessful && retryNum < maxRetry) {
            retryNum++
            Thread.sleep(delayMills)
            response = chain.proceed(request)
        }
        return response
    }
}