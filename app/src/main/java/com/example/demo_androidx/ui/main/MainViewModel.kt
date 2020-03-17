package com.example.demo_androidx.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.demo_androidx.base.BaseViewModel
import com.example.demo_androidx.constants.RemoteCode.NETWORK_ERROR
import com.example.demo_androidx.repository.Repository
import com.example.demo_androidx.repository.model.Resource
import com.example.demo_androidx.repository.model.response.ListUserResponse
import kotlinx.coroutines.*
import javax.inject.Inject
import java.lang.Exception

class MainViewModel
    @Inject constructor(val repository: Repository): BaseViewModel() {

    private val users: MutableLiveData<Resource<ListUserResponse>> by lazy {
        MutableLiveData<Resource<ListUserResponse>>()
    }

    fun getUsers(): LiveData<Resource<ListUserResponse>> {
        return users
    }

    fun loadUsers() {
        scopeMain.launch {
            users.postValue(Resource.Loading())
            Log.d("Current Thread 1", Thread.currentThread().name)
            try {
                val serviceResponse = withContext(IO) {
                    Log.d("Current Thread 2", Thread.currentThread().name)
                    repository.fetchData()
                }
                if (serviceResponse.errorCode != null) {
                    errorListener.postValue(serviceResponse.errorCode)
                    users.postValue(Resource.HideLoading())
                } else {
                    users.postValue(serviceResponse)
                }
                Log.d("Current Thread 3", Thread.currentThread().name)
            } catch (ex: Exception) {
                ex.printStackTrace()
                errorListener.postValue(NETWORK_ERROR)
                users.postValue(Resource.HideLoading())
            }
        }
    }
}
