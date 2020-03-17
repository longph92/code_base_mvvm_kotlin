package com.example.demo_androidx.repository.errormanager

import com.example.demo_androidx.App
import com.example.demo_androidx.R
import com.example.demo_androidx.constants.RemoteCode.NETWORK_ERROR
import com.example.demo_androidx.constants.RemoteCode.NO_INTERNET_CONNECTION
import com.example.demo_androidx.constants.RemoteCode.UNKNOWN_ERROR

class ErrorManager {

    companion object {
        fun getError(errorCode: Int): Error = Error(errorCode = errorCode, message = errorsMap.getValue(errorCode))

        private fun getErrorString(errorId: Int): String {
            return App.context.getString(errorId)
        }

        private val errorsMap: Map<Int, String>
            get() = mapOf(
                Pair(NO_INTERNET_CONNECTION, getErrorString(R.string.no_internet)),
                Pair(NETWORK_ERROR, getErrorString(R.string.network_error)),
                Pair(UNKNOWN_ERROR, getErrorString(R.string.unknown_error))
            ).withDefault { getErrorString(R.string.network_error) }
    }
}

class Error(
    val errorCode: Int,
    val message: String
)