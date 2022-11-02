package com.ambiws.testassignment.core.network.error

data class InternalServerError(
    val result: Int,
    val code: String,
    override val message: String,
) : RetrofitCallError(message)
