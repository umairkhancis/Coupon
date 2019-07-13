package com.noorifytech.listify.data.repository

import androidx.lifecycle.LiveData
import com.noorifytech.coupon.data.dao.backend.impl.resource.Resource
import com.noorifytech.coupon.data.vo.Item

interface ListingsRepository {
    suspend fun getListings(): LiveData<Resource<List<Item>>>
    suspend fun getNormalListings(): LiveData<Resource<List<Item>>>
    suspend fun getPromotedListings(): LiveData<Resource<List<Item>>>
    suspend fun getFeaturedListings(): LiveData<Resource<List<Item>>>
}