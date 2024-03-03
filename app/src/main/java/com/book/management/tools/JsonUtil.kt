package com.book.management.tools

import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.`$Gson$Types`
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStreamReader
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashSet

object JsonUtil {
    @JvmStatic
    val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()

    @JvmStatic
    fun toJson(obj: Any?): String {
        return gson.toJson(obj)
    }

    inline fun <reified T> genericType(): Type = object : TypeToken<T>() {}.type

    inline fun <reified T> fromJson(json: String?): T? = try {
        gson.fromJson<T>(json, TypeToken.getParameterized(T::class.java).type)
    } catch (e: Exception) {
        null
    }

    @JvmStatic
    fun <T> json2Bean(json: String?, cls: Class<T>): T? = try {
        gson.fromJson(json, cls)
    } catch (e: Exception) {
        null
    }

    inline fun <reified T> json2Bean(json: String?): T? = json2Bean(json, T::class.java)

    @JvmStatic
    fun <T> json2List(json: String?, itemType: Class<T>): MutableList<T> = try {
        val type = TypeToken.getParameterized(List::class.java, itemType).type
        val list = if (json.isNullOrBlank()) mutableListOf() else gson.fromJson<List<T?>>(json, type).filterNotNull()
        list.toMutableList()
    } catch (e: Exception) {
        mutableListOf()
    }

    @JvmStatic
    fun <T> json2Set(json: String?, itemType: Class<T>): MutableSet<T> = try {
        if (json.isNullOrBlank()) mutableSetOf<T>()
        val founderSetType = object : TypeToken<LinkedHashSet<T>>() {}.type
        gson.fromJson<LinkedHashSet<T>>(json, founderSetType)
    } catch (e: Exception) {
        mutableSetOf()
    }

    inline fun <reified T> json2List(json: String?): MutableList<T> = json2List(json, T::class.java)

    @JvmStatic
    fun <T> jsonToArrayList(json: String?, itemType: Class<T>): ArrayList<T> = try {
        val type = TypeToken.getParameterized(List::class.java, itemType).type
        val list = if (json.isNullOrBlank()) mutableListOf() else gson.fromJson<List<T?>>(json, type).filterNotNull()
        ArrayList(list)
    } catch (e: Exception) {
        ArrayList()
    }

    inline fun <reified T> jsonToArrayList(json: String?): ArrayList<T> = jsonToArrayList(json, T::class.java)

    fun <T> json2Map(json: String?, itemType: Class<T>): Map<String, T> = try {
        val type = TypeToken.getParameterized(MutableMap::class.java, String::class.java, itemType).type
        val map: Map<String, T?> = if (json.isNullOrBlank()) emptyMap() else gson.fromJson(json, type)
        map.filterValues { it != null } as Map<String, T>
    } catch (e: Exception) {
        emptyMap()
    }

    inline fun <reified T> json2Map(json: String?): Map<String, T> = json2Map(json, T::class.java)

    @JvmStatic
    fun getSuperclassTypeParameter(subclass: Class<*>): Type? {
        return try {
            val superclass = subclass.genericSuperclass
            if (superclass is Class<*>) {
                return null //throw new RuntimeException("Missing type parameter.");
            }
            val parameterized = superclass as ParameterizedType?
            `$Gson$Types`.canonicalize(parameterized!!.actualTypeArguments[0])
        } catch (e: Exception) {
            null
        }
    }

    fun readAssetText(context: Context, assetFile: String): String {
        var text = ""
        try {
            context.assets.open(assetFile).use { input ->
                InputStreamReader(input).use { reader ->
                    text = reader.readText()
                }
            }
        } catch (e: IOException) {
        }
        return text
    }
}
