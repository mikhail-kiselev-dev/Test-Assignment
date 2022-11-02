package com.ambiws.testassignment.core.network.error

open class RetrofitCallError(
    override val message: String,
    override val cause: Throwable? = null
) : Exception(message, cause)
