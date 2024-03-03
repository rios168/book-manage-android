package com.book.management.bean

class PageResponse<T> {
    var pageNo = 0
    var pageSize = 0
    var totalCount = 0L
    var records: List<T> = emptyList()

    val hasNext: Boolean
        get() =
            if (totalCount != 0L) pageNo * pageSize < totalCount
            else records.size == pageSize

    val data: List<T> get() = records

    val hasNext2: Boolean
        get() =
            if (records == null || records.size < pageSize) false
            else true

}