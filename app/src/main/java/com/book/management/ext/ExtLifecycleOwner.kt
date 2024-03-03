package com.book.management.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.book.management.bean.HintException
import com.book.management.tools.LogUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

/**
 * Created by Mark on 2022/8/25.
 */
fun <T> CoroutineScope.onEach(action: Flow<T>,
                              loading: ((Boolean) -> Unit)? = null,
                              retryCount: Long = 0,
                              retryDelay: Long = 1000,
                              timeout: Long = 0,
                              catch: (suspend (Throwable) -> Unit)? = null,
                              onStart: (suspend () -> Unit)? = null,
                              onCompletion: (suspend () -> Unit)? = null,
                              onEach: (suspend (T) -> Unit)? = null) {
    var launchJob: Job? = null
    var delayJob: Job? = null
    if (timeout > 0) {
        delayJob = this.launch {
            delay(timeout)
            launchJob?.cancel()
            val hintException = HintException("连接超时，请检查网络")
            onFlowError(hintException)
            catch?.invoke(hintException)
        }
    }
    var onFlow = action.onEach {
        delayJob?.cancel()
        onEach?.invoke(it)
    }
    if (retryCount > 0) {
        onFlow = onFlow.retry(retryCount) {
            delay(retryDelay)
            true
        }
    }
    launchJob = onFlow.catch {
        delayJob?.cancel()
        onFlowError(it)
        catch?.invoke(it)
        LogUtil.e(it)
    }.onStart {
        loading?.invoke(true)
        onStart?.invoke()
    }.onCompletion {
        onCompletion?.invoke()
        loading?.invoke(false)
    }.catch {
        LogUtil.e(it)
        LogUtil.e("代码错误!")
    }.launchIn(this)
}

fun <T> Flow<T>.onEachTry(scope: CoroutineScope, action: suspend (T) -> Unit) {
    this.onEach(action).catch { LogUtil.e(it) }.launchIn(scope)
}

fun <T> Flow<T>.onEachTry(owner: LifecycleOwner, action: suspend (T) -> Unit) {
    onEachTry(owner.lifecycleScope, action)
}

fun LifecycleOwner.runMain(delayMilli: Long = 0, block: suspend () -> Unit) {
    runDelay(lifecycleScope, Dispatchers.Main, delayMilli, block)
}

fun LifecycleOwner.runIO(delayMilli: Long = 0, block: suspend () -> Unit) {
    runDelay(lifecycleScope, Dispatchers.IO, delayMilli, block)
}

private fun runDelay(scope: CoroutineScope,
                     context: CoroutineContext,
                     delayMilli: Long = 0,
                     block: suspend () -> Unit) {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, e ->
        LogUtil.e(e)
    }
    scope.launch(context + exceptionHandler) {
        if (delayMilli > 0) delay(delayMilli)
        block()
    }
}


fun <R> tryCatch(def: R? = null, block: () -> R): R? {
    try {
        return block()
    } catch (th: Throwable) {
        LogUtil.e(th)
    }
    return def
}


/** 绑带生命周期, 每秒回调一次,结束后也回调
 * @param expireSec 倒数秒数
 * @param onNext 每秒回调一次, 参数为剩余秒数
 */
fun LifecycleOwner.countingDown(
    expireSec: Int = 60,
    onStart: (() -> Unit)? = null,
    onComplete: (() -> Unit)? = null,
    onNext: ((Int) -> Unit)? = null,
): Job = lifecycleScope.launch {
    onStart?.invoke()
    for (i in expireSec downTo 1) {
        onNext?.invoke(i)
        delay(1000L)
    }
    onComplete?.invoke()
}
