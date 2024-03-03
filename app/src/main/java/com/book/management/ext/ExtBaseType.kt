package com.book.management.ext

import com.book.management.tools.JsonUtil
import com.book.management.tools.LogUtil
import okio.Buffer
import java.io.EOFException
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * Created by Mark on 2022/8/24.
 * 基本数据类型扩展
 */

val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

val String.fileName get() = substringAfterLast('/').substringBefore('?')
val String.fileExt get() = "." + fileName.substringAfter('.').lowercase()
val String.isHttp get() = startsWith("http://") || startsWith("https://")
val String.isAsset get() = startsWith("file:///android_asset/")
fun Any.toJson(): String {
    return JsonUtil.toJson(this)
}

fun randomString(len: Int): String {
    return (1..len)
        .map { kotlin.random.Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")
}


val Int?.isNullOrZero get() = this == null || this == 0
val Long?.isNullOrZero get() = this == null || this == 0L

fun Float.toDecimalFormat(show: String = "#.##"): String {
    val format = DecimalFormat(show)
    format.roundingMode = RoundingMode.FLOOR//舍弃后面
    return format.format(this)
}


inline fun String.toIntTry(def: Int = 0): Int = try {
    toInt()
} catch (th: Exception) {
    LogUtil.e(th)
    def
}


fun Buffer.isProbablyUtf8(): Boolean {
    return try {
        val prefix = Buffer()
        val byteCount = size.coerceAtMost(64L)
        copyTo(prefix, 0L, byteCount)
        var var4 = 0
        val var5: Byte = 16
        while (var4 < var5 && !prefix.exhausted()) {
            val codePoint = prefix.readUtf8CodePoint()
            if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                return false
            }
            ++var4
        }
        true
    } catch (var7: EOFException) {
        false
    }
}


