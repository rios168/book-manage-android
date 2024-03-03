package com.book.management.http

import com.blankj.utilcode.util.Utils
import com.book.management.Constant
import com.book.management.ext.SingletonMap
import com.book.management.tools.JsonUtil
import com.book.management.tools.LogUtil
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.net.Proxy
import java.util.concurrent.TimeUnit

object HttpKtx : SingletonMap<Retrofit, String>() {
    private val cacheSize = Cache(File(Utils.getApp().cacheDir, "http_cache"), 10L * 1024 * 1024)

    @JvmStatic
    val okhttp = OkHttpClient.Builder().apply {
        cache(cacheSize)
        addInterceptor(InterceptorHeader()) //        addInterceptor(CacheIntercepter())
        //        addInterceptor(RetryIntercepter())
        if (LogUtil.isDebug) {
            addInterceptor(InterceptorLogging()) //            addInterceptor(InterceptorMock())
        }
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
        hostnameVerifier { _, _ -> true }
        if (!LogUtil.isDebug) proxy(Proxy.NO_PROXY)
    }.build()

    override val creator = { baseUrl: String ->
        LogUtil.d("baseUrl===$baseUrl")
        Retrofit.Builder().client(okhttp).baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(JsonUtil.gson)).build()
    }

    fun getUrl(url: String?): String {
        var url = url ?: ""
        if (url.isNotBlank()) {
            if (url.startsWith("/") || url.startsWith("http")) {
            } else { //photo,room
                //                url = SyncStateContract.Constants.FILE_HTTPS + url
            }
        }
        return url
    }

    val baseApi = instanceFor(Constant.SERVICE_IP).create(APIServiceKtx::class.java)
}



