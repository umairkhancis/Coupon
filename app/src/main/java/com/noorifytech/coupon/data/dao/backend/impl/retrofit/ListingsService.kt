package com.noorifytech.coupon.data.dao.backend.impl.retrofit

import com.noorifytech.coupon.data.dao.backend.dto.Item
import com.noorifytech.coupon.data.dao.backend.dto.ListingsResponse
import com.noorifytech.coupon.data.dao.backend.impl.resource.ApiResponse
import retrofit2.http.GET

interface ListingsService {
    @GET("/listings")
    suspend fun getNormalListings(): ApiResponse<ListingsResponse<List<Item>>>

    @GET("/promoted/listings")
    suspend fun gePromotedListings(): ApiResponse<ListingsResponse<List<Item>>>

    @GET("/featured/listings")
    suspend fun getFeaturedListings(): ApiResponse<ListingsResponse<List<Item>>>
}