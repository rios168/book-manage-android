package com.book.management.tools

import android.util.Log

object LogUtil {
    const val DEFAULT_TAG = "打印信息----"
    val isDebug = true

    fun d(logText: String) {
        if (isDebug) {
            Log.d(DEFAULT_TAG, logText)
        }
    }

    fun d(tag: String, logText: String) {
        if (isDebug) {
            Log.d(DEFAULT_TAG, "$tag   $logText")
        }
    }


    fun e(logText: String) {
        if (isDebug) {
            Log.e(DEFAULT_TAG, logText)
        }
    }

    fun e(th: Throwable) {
        if (isDebug) {
            Log.e(DEFAULT_TAG, "异常错误", th)
        }
    }

    /**
     * 超长log
     *
     */
    fun dd(tag: String, msg: String) { //采取分段打印日志的方法：当长度超过4000时，我们就来分段截取打印
        //剩余的字符串长度如果大于4000
        var msg = msg
        if (isDebug) {
            if (tag == null || msg == null || tag.length == 0 || msg.length == 0) return
            val segmentSize = 10 * 1024
            val length = msg.length.toLong()
            if (length <= segmentSize) { // 长度小于等于限制直接打印
                Log.d(tag, msg)
            } else {
                while (msg!!.length > segmentSize) { // 循环分段打印日志
                    val logContent = msg.substring(0, segmentSize)
                    msg = msg.replace(logContent, "")
                    Log.d(tag, logContent)
                }
                Log.d(tag, msg) // 打印剩余日志
            }
        }
    }

}