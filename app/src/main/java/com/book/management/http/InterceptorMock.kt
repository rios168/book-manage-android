package com.book.management.http

import com.book.management.bean.DataResponse
import com.book.management.tools.JsonUtil
import com.book.management.tools.LogUtil
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException


/**
 * Created by Mark on 2022/8/26.
 */
class InterceptorMock : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        LogUtil.d(" request.url:${request.url}")

        val responseString: String = JsonUtil.toJson(
            DataResponse(0, ("123"), "你好鸭")
        )
        val response = Response.Builder()
            .code(200)
            .message("自动本地回复:")
            .request(request)
            .protocol(Protocol.HTTP_1_0)
            .body(responseString.toByteArray().toResponseBody("application/json".toMediaTypeOrNull()))
            .addHeader("content-type", "application/json")
            .build()
        return response
    }
}