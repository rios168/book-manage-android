package com.book.management.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*

/**
 * Created by Mark on 2022/8/25.
 */
open class BaseViewModel<E> : ViewModel() {

    fun <T> onFlow(action: suspend () -> T) = flow { emit(action()) }

    val eventFlow = MutableSharedFlow<E>()
}

