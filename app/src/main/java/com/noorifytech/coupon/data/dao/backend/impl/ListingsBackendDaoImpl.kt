package com.noorifytech.coupon.data.dao.backend.impl

import com.noorifytech.coupon.data.dao.backend.ListingsBackendDao
import com.noorifytech.coupon.data.dao.backend.impl.retrofit.ListingsService

class ListingsBackendDaoImpl(private val listingsService: ListingsService) : ListingsBackendDao {

    override suspend fun getNormalListings() = listingsService.getNormalListings()

    override suspend fun getPromotedListings() = listingsService.gePromotedListings()

    override suspend fun getFeaturedListings() = listingsService.getFeaturedListings()
}