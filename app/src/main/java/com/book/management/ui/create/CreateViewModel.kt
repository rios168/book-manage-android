package com.book.management.ui.create

import androidx.lifecycle.viewModelScope
import com.book.management.base.BaseViewModel
import com.book.management.bean.BookBean
import com.book.management.ext.onEach
import com.book.management.http.HttpKtx
import com.book.management.tools.LogUtil
import kotlinx.coroutines.CoroutineScope

class CreateViewModel : BaseViewModel<CreateEvent>() {

    fun dispatch(event: CreateAction) {
        when (event) {
            is CreateAction.CreateBook -> {
                createBook(event.bookBean)
            }
        }
    }

    fun createBook(bookBean: BookBean) {
        viewModelScope.onEach(onFlow { HttpKtx.baseApi.createBook(bookBean) }, catch = {
            eventFlow.emit(CreateEvent.Error(it.message ?: ""))
        }) {
            LogUtil.d(it.data.toString())
            eventFlow.emit(CreateEvent.Success(it.data!!))
        }
    }
}

sealed class CreateEvent {
    data class Error(val msg: String) : CreateEvent()
    data class Success(val bookId: Long) : CreateEvent()
}

sealed class CreateAction {
    data class CreateBook(val bookBean: BookBean) : CreateAction()
}