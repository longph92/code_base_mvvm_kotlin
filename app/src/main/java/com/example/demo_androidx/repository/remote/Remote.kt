package com.example.demo_androidx.repository.remote

import com.example.demo_androidx.App
import com.example.demo_androidx.constants.RemoteCode.NETWORK_ERROR
import com.example.demo_androidx.constants.RemoteCode.NO_INTERNET_CONNECTION
import com.example.demo_androidx.constants.RemoteCode.UNKNOWN_ERROR
import com.example.demo_androidx.repository.model.Resource
import com.example.demo_androidx.repository.model.response.ListUserResponse
import com.example.demo_androidx.utils.Network.Utils.isConnected
import retrofit2.Response
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class Remote @Inject constructor(private val apiService: ApiService) {

    suspend fun fetchData(): Resource<ListUserResponse> {
        return when (val response = processCall(apiService::fetchData)) {
            is ListUserResponse -> {
                Resource.Success(data = response)
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!isConnected(App.context)) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            e.printStackTrace()
            NETWORK_ERROR
        } catch (ex: Exception) {
            UNKNOWN_ERROR
        }
    }
}