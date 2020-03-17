package com.example.demo_androidx.repository.model.response

import com.example.demo_androidx.repository.model.User
import com.google.gson.annotations.SerializedName

data class ListUserResponse(
    @SerializedName("page")
    val page: Int,

    @SerializedName("per_page")
    val perPage: Int,

    @SerializedName("total_pages")
    val totalPage: Int,

    @SerializedName("total")
    val total: Int,

    @SerializedName("data")
    val data: List<User>
)