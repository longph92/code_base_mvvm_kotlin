package com.example.demo_androidx.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

abstract class BaseViewModel : ViewModel() {
    protected val scopeMain = CoroutineScope(Dispatchers.Main)
    protected val IO = Dispatchers.IO

    val errorListener: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    override fun onCleared() {
        super.onCleared()
        scopeMain.cancel()
    }
}