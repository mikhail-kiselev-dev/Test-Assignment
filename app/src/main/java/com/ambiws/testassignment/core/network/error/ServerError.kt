package com.ambiws.testassignment.core.network.error

data class ServerError(
    val code: Int
) : RetrofitCallError(code.toErrorMessage()) {
    override fun toString(): String {
        return code.toErrorMessage()
    }
}

private fun Int.toErrorMessage(): String =
    "Unexpected server error with code: $this"
