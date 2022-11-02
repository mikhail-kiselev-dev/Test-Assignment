package com.ambiws.testassignment.core.extensions

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.reflect.KClass

const val TIME_MILLIS_50 = 50L

fun <T> Fragment.subscribe(liveData: (LiveData<T>)?, onNext: (t: T) -> Unit) {
    liveData?.observe(viewLifecycleOwner) {
        if (it != null) {
            onNext(it)
        }
    }
}

fun <T> Fragment.subscribeResult(liveData: (LiveData<T>)?, onNext: (t: T) -> Unit) {
    liveData?.observe(viewLifecycleOwner) {
        lifecycleScope.launchWhenResumed {
            delay(TIME_MILLIS_50)
            if (it != null) {
                onNext(it)
            }
        }
    }
}

fun <T> Fragment.subscribeNullable(liveData: LiveData<T>?, onNext: (t: T?) -> Unit) {
    liveData ?: return
    liveData.observe(viewLifecycleOwner, Observer { onNext(it) })
}

fun <T> FragmentActivity.subscribe(liveData: (LiveData<T>)?, onNext: (t: T) -> Unit) {
    liveData?.observe(this) {
        if (it != null) {
            onNext(it)
        }
    }
}

fun <T> FragmentActivity.subscribeNullable(liveData: LiveData<T>, onNext: (t: T?) -> Unit) {
    liveData.observe(this) { onNext(it) }
}

fun <T> Fragment.subscribe(flow: (Flow<T?>)?, onNext: (t: T) -> Unit) {
    flow ?: return
    lifecycleScope.launchWhenResumed {
        flow.collect {
            if (it != null) {
                onNext(it)
            }
        }
    }
}

fun <T> Fragment.subscribeNullable(flow: (Flow<T?>)?, onNext: (t: T?) -> Unit) {
    flow ?: return
    lifecycleScope.launchWhenResumed {
        flow.collect { onNext(it) }
    }
}

fun <T> FragmentActivity.subscribe(flow: (Flow<T?>)?, onNext: (t: T) -> Unit) {
    flow ?: return
    lifecycleScope.launchWhenResumed {
        flow.collect {
            if (it != null) {
                onNext(it)
            }
        }
    }
}

fun <T> FragmentActivity.subscribeNullable(flow: (Flow<T?>)?, onNext: (t: T?) -> Unit) {
    flow ?: return
    lifecycleScope.launchWhenResumed {
        flow.collect { onNext(it) }
    }
}

fun <T> DialogFragment.subscribe(liveData: (LiveData<T>)?, onNext: (t: T) -> Unit) {
    liveData?.observe(viewLifecycleOwner) {
        if (it != null) {
            onNext(it)
        }
    }
}

fun <T : FragmentActivity> FragmentActivity.startClearActivity(kClass: KClass<T>) {
    finish()
    startActivity(
        Intent(this, kClass.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
    )
}

fun <T> MutableLiveData<T>.callAndNullify(message: T) {
    value = message
    value = null
}

fun FragmentActivity.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    globalToast(this, text, duration)

fun FragmentActivity.showToast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) =
    globalToast(this, getString(resId), duration)

fun Fragment.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    requireActivity().showToast(text, duration)

fun Fragment.showToast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) =
    requireActivity().showToast(resId, duration)

private fun globalToast(context: Context, text: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(context, text, duration).show()

inline fun <T> LiveData<T>.mutable(): MutableLiveData<T> =
    (this as? MutableLiveData) ?: throw IllegalArgumentException("LiveData is not mutable")

inline fun <T> StateFlow<T>.mutable(): MutableStateFlow<T> =
    (this as? MutableStateFlow) ?: throw IllegalArgumentException("StateFlow is not mutable")
