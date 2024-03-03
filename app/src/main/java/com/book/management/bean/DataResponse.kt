package com.book.management.bean

class DataResponse<T>(val code: Int = 0, val data: T? = null, val message: String? = null) {

    fun validate(string: String): T {
        if (code != 0) throw HintException(message)
        if (data == null) throw HintException(string)
        return data
    }
}

open class HintException(msg: String?) : Exception(msg)
