package com.noorifytech.coupon.data.dao.backend

import com.noorifytech.coupon.data.dao.backend.dto.Item
import com.noorifytech.coupon.data.dao.backend.dto.ListingsResponse
import com.noorifytech.coupon.data.dao.backend.impl.resource.ApiResponse

interface ListingsBackendDao {
    suspend fun getNormalListings(): ApiResponse<ListingsResponse<List<Item>>>
    suspend fun getPromotedListings(): ApiResponse<ListingsResponse<List<Item>>>
    suspend fun getFeaturedListings(): ApiResponse<ListingsResponse<List<Item>>>
}