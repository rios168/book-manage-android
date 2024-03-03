package com.book.management.ui.detail

import androidx.lifecycle.viewModelScope
import com.book.management.base.BaseViewModel
import com.book.management.bean.BookBean
import com.book.management.ext.onEach
import com.book.management.http.HttpKtx
import com.book.management.tools.LogUtil

class DetailViewModel : BaseViewModel<DetailEvent>() {

    fun dispatch(event: DetailAction) {
        when (event) {
            is DetailAction.Update -> {
                updateBook(event.bookBean)
            }

            is DetailAction.Delete -> {
                deleteBook(event.bookId)
            }
        }
    }

    fun updateBook(bookBean: BookBean) {
        viewModelScope.onEach(onFlow { HttpKtx.baseApi.modifyBook(bookBean) }, catch = {
            eventFlow.emit(DetailEvent.Error(it.message ?: ""))
        }) {
            LogUtil.d(it.data.toString())
            eventFlow.emit(DetailEvent.UpdateSuccess)
        }
    }

    fun deleteBook(bookId: Long) {
        viewModelScope.onEach(onFlow { HttpKtx.baseApi.deleteBook(bookId) }, catch = {
            eventFlow.emit(DetailEvent.Error(it.message ?: ""))
        }) {
            LogUtil.d(it.data.toString())
            eventFlow.emit(DetailEvent.DeleteSuccess)
        }
    }
}

sealed class DetailEvent {
    data class Error(val msg: String) : DetailEvent()
    object DeleteSuccess : DetailEvent()
    object UpdateSuccess : DetailEvent()
}

sealed class DetailAction {
    data class Delete(val bookId: Long) : DetailAction()
    data class Update(val bookBean: BookBean) : DetailAction()
}