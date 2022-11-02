package com.ambiws.testassignment.core.network.error.parser

import com.ambiws.testassignment.core.network.error.ErrorConstants.INTERNAL_SERVER_ERROR_CODE
import com.ambiws.testassignment.core.network.error.FromServerError
import com.ambiws.testassignment.core.network.error.InternalServerError
import com.ambiws.testassignment.core.network.error.NetworkError
import com.ambiws.testassignment.core.network.error.ServerError
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection

class CallWithErrorHandling(
    private val delegate: Call<Any>,
) : Call<Any> by delegate {

    override fun enqueue(callback: Callback<Any>) {
        delegate.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    callback.onResponse(call, response)
                } else {
                    trackException(HttpException(response), callback, call)
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                trackException(t, callback, call)
            }
        })
    }

    private fun trackException(t: Throwable, callback: Callback<Any>, call: Call<Any>) {
        val appException = mapToAppException(t)
        callback.onFailure(call, appException)

        /* TODO uncomment on Crashlytics implemented
        FirebaseCrashlytics.getInstance()
            .recordException(appException)
        */
    }

    private fun mapToAppException(throwable: Throwable): Throwable {
        return when (throwable) {
            is HttpException -> {
                val errorResponse = throwable.response()?.errorBody()?.string()
                when (throwable.code()) {
                    HttpURLConnection.HTTP_UNAUTHORIZED -> {
                        FromServerError(
                            errorResponse ?: "unauthorized error",
                            throwable.code(),
                            throwable
                        )
                    }
                    HttpURLConnection.HTTP_NOT_FOUND -> {
                        val errorMessage = if (errorResponse != null) {
                            try {
                                val json = JSONObject(errorResponse)
                                json.getString("error")
                            } catch (e: JSONException) {
                                "not found error"
                            }
                        } else {
                            "not found error"
                        }
                        FromServerError(
                            errorMessage,
                            throwable.code(),
                            throwable
                        )
                    }
                    INTERNAL_SERVER_ERROR_CODE -> {
                        if (errorResponse != null) {
                            val json = JSONObject(errorResponse)
                            InternalServerError(
                                if (json.isNull("result")) 0 else json.getInt("result"),
                                json.getString("code"),
                                json.getString("message"),
                            )
                        } else {
                            FromServerError(
                                "internal server error",
                                throwable.code(),
                                throwable
                            )
                        }
                    }
                    else -> {
                        ServerError(throwable.code())
                    }
                }
            }
            else -> {
                NetworkError(throwable)
            }
        }
    }
}
