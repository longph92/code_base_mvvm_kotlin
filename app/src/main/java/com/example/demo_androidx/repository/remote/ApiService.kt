package com.example.demo_androidx.repository.remote

import com.example.demo_androidx.repository.model.response.ListUserResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("api/users?page=1")
    suspend fun fetchData(): Response<ListUserResponse>
}