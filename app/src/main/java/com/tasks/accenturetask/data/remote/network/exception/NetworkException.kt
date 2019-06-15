package com.tasks.accenturetask.data.remote.network.exception

import com.tasks.accenturetask.base.domain.exception.AccentureException
import retrofit2.Response
import java.io.IOException

object NetworkException {
    fun httpError(response: Response<Any>?): AccentureException {
        val message: String? = null
        var responseBody = ""
        var statusCode = 0
        val errorCode = 0
        response?.let { statusCode = it.code() }
        response?.let {
            responseBody = it.errorBody()!!.string()
            try {
                // in case of handle http API error
            } catch (exception: Exception) {
            }
        }

        var kind = AccentureException.Kind.HTTP
        when (statusCode) {
            500 -> kind = AccentureException.Kind.SERVER_DOWN
            408 -> kind = AccentureException.Kind.TIME_OUT
            401 -> kind = AccentureException.Kind.UNAUTHORIZED
        }

        return AccentureException(kind, message?.let { message }
            ?: run { "" })
            .setErrorCode(errorCode)
            .setStatusCode(statusCode)
            .setData(responseBody)
    }

    fun networkError(exception: IOException): AccentureException {
        return AccentureException(AccentureException.Kind.NETWORK, exception)
    }

    fun unexpectedError(exception: Throwable): AccentureException {
        return AccentureException(AccentureException.Kind.UNEXPECTED, exception)
    }
}