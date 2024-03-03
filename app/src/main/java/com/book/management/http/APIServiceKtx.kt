package com.book.management.http

import com.book.management.bean.BookBean
import com.book.management.bean.DataResponse
import retrofit2.http.*

interface APIServiceKtx {

    @GET("detail/getBook")
    suspend fun getBook(@Query("bookId") bookId: Long): DataResponse<BookBean>

    @GET("detail/getAllBook")
    suspend fun getAllBook(): DataResponse<MutableList<BookBean>>

    @POST("update/create")
    suspend fun createBook(@Body bookBean: BookBean): DataResponse<Long>

    @POST("update/modify")
    suspend fun modifyBook(@Body bookBean: BookBean): DataResponse<Int>

    @GET("update/delete")
    suspend fun deleteBook(@Query("bookId") bookId: Long): DataResponse<Int>
}
