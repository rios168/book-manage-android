package com.book.management.ui.list

import androidx.lifecycle.viewModelScope
import com.book.management.base.BaseViewModel
import com.book.management.bean.BookBean
import com.book.management.ext.onEach
import com.book.management.http.HttpKtx
import com.book.management.tools.LogUtil

class ListViewModel : BaseViewModel<ListEvent>() {

    fun dispatch(event: ListAction) {
        when (event) {
            is ListAction.UpdateList -> {
                getBookList()
            }
        }
    }

    fun getBookList() {
        viewModelScope.onEach(onFlow { HttpKtx.baseApi.getAllBook() }, catch = {
            eventFlow.emit(ListEvent.Error)
        }) {
            LogUtil.d(it.data.toString())
            eventFlow.emit(ListEvent.GetBookList(it.data!!))
        }
    }
}

sealed class ListEvent {
    object Error : ListEvent()
    data class GetBookList(val bookList: MutableList<BookBean>) : ListEvent()
}

sealed class ListAction {
    object UpdateList : ListAction()
}