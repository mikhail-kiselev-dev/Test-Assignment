package com.ambiws.testassignment.core.network.error.parser

import retrofit2.Call
import retrofit2.CallAdapter

class ErrorCallAdapter(
    private val delegateAdapter: CallAdapter<Any, Call<*>>,
) : CallAdapter<Any, Call<*>> by delegateAdapter {

    override fun adapt(call: Call<Any>): Call<*> {
        return delegateAdapter.adapt(CallWithErrorHandling(call))
    }
}
