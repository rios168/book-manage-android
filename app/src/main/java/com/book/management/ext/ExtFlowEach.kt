package com.book.management.ext

import com.book.management.bean.HintException
import com.book.management.tools.LogUtil
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

/**
 * Created by Mark on 2022/5/2.
 */

fun onFlowError(e: Throwable) {
    LogUtil.e(e)
    when (e) {
        is TimeoutException -> {
            LogUtil.e("更新连接超时，请检查网络")
        }
        is TimeoutCancellationException -> {
            LogUtil.e("连接超时，请检查网络")
        }
        is IOException -> {
            LogUtil.e("网络不佳，请确保网络连接通畅后再试")
        }
        is HttpException -> {
            if (LogUtil.isDebug) {
                val s = "连接超时，请检查网络"
                LogUtil.e(s)
            } else {
                LogUtil.e("连接超时，请检查网络")
            }
        }
        is HintException -> {
            e.message?.let { LogUtil.e(it) }
        }
        else -> e.message?.let { LogUtil.e(it) }
    }
}
