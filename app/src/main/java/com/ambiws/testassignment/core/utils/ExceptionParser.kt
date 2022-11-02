package com.ambiws.testassignment.core.utils

import com.ambiws.testassignment.R
import com.ambiws.testassignment.core.network.error.FromServerError
import com.ambiws.testassignment.core.network.error.InternalServerError
import com.ambiws.testassignment.core.network.error.NetworkError
import com.ambiws.testassignment.core.network.error.ServerError
import com.ambiws.testassignment.core.providers.ResourceProvider
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException

class ExceptionParser(
    private val resourceProvider: ResourceProvider
) {
    fun parseError(throwable: Throwable): String {
        throwable.printStackTrace()
        return when (throwable) {
            is ServerError -> {
                return throwable.toString().capitalize()
            }
            is FromServerError -> {
                return throwable.message.capitalize()
            }
            is NetworkError -> {
                when (throwable.origin) {
                    is ConnectException,
                    is SocketTimeoutException,
                    is SocketException -> {
                        return resourceProvider.getString(R.string.connection_error)
                    }
                    is InterruptedIOException -> {
                        return resourceProvider.getString(R.string.network_error)
                    }
                    else -> {
                        throwable.origin.message?.capitalize()
                            ?: resourceProvider.getString(R.string.unexpected_network_error)
                    }
                }
            }
            is InternalServerError -> {
                return throwable.message.capitalize()
            }
            else -> {
                throwable.localizedMessage?.capitalize()
                    ?: resourceProvider.getString(R.string.unexpected_server_error)
            }
        }
    }
}
