package com.ambiws.testassignment.core.providers

import kotlinx.coroutines.flow.Flow

interface PreferencesProvider {

    fun saveString(key: String, value: String?)

    fun getString(key: String): String?

    fun saveInt(key: String, value: Int)

    fun getInt(key: String): Int

    fun getIntFlow(key: String): Flow<Int>

    fun getBooleanFlow(key: String): Flow<Boolean>

    fun getStringFlow(key: String): Flow<String?>

    fun saveLong(key: String, value: Long)

    fun getLong(key: String): Long

    fun saveBoolean(key: String, value: Boolean)

    fun getBoolean(key: String): Boolean

    fun getBooleanWithDefaultValue(key: String, default: Boolean): Boolean

    fun hasProperty(key: String): Boolean

    fun removeValue(key: String)

    fun clearAll()

    fun addToStringSet(key: String, value: String)

    fun getStringSet(key: String): MutableSet<String>

    fun clear(key: String)
}
