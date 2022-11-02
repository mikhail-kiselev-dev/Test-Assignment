package com.ambiws.testassignment.core.providers

import android.content.Context
import android.content.SharedPreferences
import com.ambiws.testassignment.core.extensions.offerCatching
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

private const val PREFERENCES = "tas_preferences"

class PreferencesProviderImpl(context: Context) : PreferencesProvider {

    private val sharedPreferences =
        context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)

    override fun saveString(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun getString(key: String): String? = sharedPreferences.getString(key, null)

    override fun saveInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    override fun getInt(key: String): Int = sharedPreferences.getInt(key, 0)

    override fun getIntFlow(key: String): Flow<Int> = callbackFlow {
        val preferencesListener =
            SharedPreferences.OnSharedPreferenceChangeListener { _, updatedKey ->
                if (updatedKey == key) {
                    offerCatching(getInt(key))
                }
            }

        offerCatching(getInt(key))
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferencesListener)
        awaitClose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(preferencesListener)
        }
    }

    override fun getBooleanFlow(key: String): Flow<Boolean> = callbackFlow {
        offerCatching(getBoolean(key))
        val preferencesListener =
            SharedPreferences.OnSharedPreferenceChangeListener { _, updatedKey ->
                if (updatedKey == key) {
                    offerCatching(getBoolean(key))
                }
            }

        sharedPreferences.registerOnSharedPreferenceChangeListener(preferencesListener)
        awaitClose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(preferencesListener)
        }
    }

    override fun getStringFlow(key: String): Flow<String?> = callbackFlow {
        val preferencesListener =
            SharedPreferences.OnSharedPreferenceChangeListener { _, updatedKey ->
                if (updatedKey == key) {
                    offerCatching(getString(key))
                }
            }

        offerCatching(getString(key))
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferencesListener)
        awaitClose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(preferencesListener)
        }
    }

    override fun saveLong(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    override fun getLong(key: String): Long = sharedPreferences.getLong(key, 0)

    override fun saveBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    override fun getBoolean(key: String): Boolean = sharedPreferences.getBoolean(key, false)

    override fun getBooleanWithDefaultValue(key: String, default: Boolean): Boolean =
        sharedPreferences.getBoolean(key, default)

    override fun hasProperty(key: String): Boolean = sharedPreferences.contains(key)

    override fun removeValue(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    override fun clearAll() {
        sharedPreferences.edit().clear().commit()
    }

    override fun addToStringSet(key: String, value: String) {
        val set = getStringSet(key)
        set.add(value)
        sharedPreferences.edit().putStringSet(key, set).apply()
    }

    override fun getStringSet(key: String): MutableSet<String> =
        sharedPreferences.getStringSet(key, HashSet()) ?: HashSet()

    override fun clear(key: String) {
        removeValue(key)
    }
}
