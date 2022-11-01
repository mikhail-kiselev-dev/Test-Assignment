package com.ambiws.testassignment.core.utils

import android.util.Log
import com.ambiws.testassignment.BuildConfig

private val loggingEnabled = BuildConfig.DEBUG

fun Any.loge(msg: String?) {
    if (loggingEnabled) {
        Log.e("e".plus(TAG), msg ?: "null")
    }
}

fun Any.loge(msg: String?, throwable: Throwable?) {
    if (loggingEnabled) {
        Log.e("e".plus(TAG), msg ?: "null", throwable ?: Throwable())
    }
}

fun Any.logd(msg: String?) {
    if (loggingEnabled) {
        Log.d("d".plus(TAG), msg ?: "null")
    }
}

fun Any.logw(msg: String?) {
    if (loggingEnabled) {
        Log.w("w".plus(TAG), msg ?: "null")
    }
}

fun Any.logi(msg: String?) {
    if (loggingEnabled) {
        Log.i("i".plus(TAG), msg ?: "null")
    }
}

fun Any.logv(msg: String?) {
    if (loggingEnabled) {
        Log.v("v".plus(TAG), msg ?: "null")
    }
}

fun Any.logwtf(msg: String?) {
    if (loggingEnabled) {
        Log.wtf("f".plus(TAG), msg ?: "null")
    }
}

val <T : Any> T.TAG: String
    get() = if (this::class.java.simpleName.length > 16)
        "TAS_".plus(this::class.java.simpleName.substring(0, 15))
    else
        "TAS_".plus(this::class.java.simpleName)
