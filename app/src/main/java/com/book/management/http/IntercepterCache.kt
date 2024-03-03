package com.book.management.http

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * Created by Mark on 2022/8/26.
 */
class IntercepterCache() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val maxStale = 30 // 离线时缓存保存4周,单位:秒
        val tempCacheControl: CacheControl = CacheControl.Builder()
            .maxStale(maxStale, TimeUnit.SECONDS)
            .build()
        request = request.newBuilder()
            .cacheControl(tempCacheControl)
            .build()
        return chain.proceed(request)
    }
}