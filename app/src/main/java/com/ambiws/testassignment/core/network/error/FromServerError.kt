package com.ambiws.testassignment.core.network.error

data class FromServerError(
    override val message: String,
    val code: Int,
    override val cause: Throwable?,
) : RetrofitCallError(message, cause)
