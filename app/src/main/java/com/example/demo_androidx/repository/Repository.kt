package com.example.demo_androidx.repository

import com.example.demo_androidx.repository.model.Resource
import com.example.demo_androidx.repository.model.response.ListUserResponse
import com.example.demo_androidx.repository.remote.Remote
import javax.inject.Inject

class Repository @Inject constructor(
    private val remote: Remote
) {
    suspend fun fetchData(): Resource<ListUserResponse> = remote.fetchData()

}