package com.ambiws.testassignment.core.network.error

data class NetworkError(
    val origin: Throwable
) : RetrofitCallError(origin.message ?: "undefined network error")
