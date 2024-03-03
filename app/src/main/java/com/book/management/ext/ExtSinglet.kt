package com.book.management.ext

import java.util.concurrent.ConcurrentHashMap

/**
 * Created by Mark on 2022/8/25.
 */

/**
 * 参数初始化的，线程安全的，单体
 */
open class SingletHolder<out T>(val creator: () -> T) {

    @Volatile
    private var _instance: T? = null

    fun reset(): T {
        _instance = null
        return instance
    }

    val instance: T
        get() = _instance ?: synchronized(this) {
            _instance ?: creator().also { _instance = it }
        }
}

open class SingletVal<out T>(val creator: () -> T) {

    val instance: T by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        creator()
    }
}

/**
 * 参数初始化的，线程安全的，单体
 */
open class SingletonHolder<out T, in A>(val creator: (A) -> T) {

    @Volatile
    private var _instance: T? = null

    fun instance(arg: A): T = _instance ?: synchronized(this) {
        _instance ?: creator(arg).also { _instance = it }
    }
}

/**
 * 多参数初始化,多输出
 */
abstract class SingletonMap<out T, in A : Any> {
    private val objMap = ConcurrentHashMap<A, T>()
    abstract val creator: (A) -> T

    fun instanceFor(arg: A): T {
        return objMap[arg] ?: creator(arg).also { objMap[arg] = it }
    }
}