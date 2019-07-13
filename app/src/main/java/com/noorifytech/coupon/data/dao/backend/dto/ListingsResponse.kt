package com.noorifytech.coupon.data.dao.backend.dto

import com.google.gson.annotations.SerializedName

data class ListingsResponse<T>(
    @SerializedName("data")
    val data: T,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
)