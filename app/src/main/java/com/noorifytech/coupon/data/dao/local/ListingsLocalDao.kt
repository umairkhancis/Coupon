package com.noorifytech.coupon.data.dao.local

import com.noorifytech.coupon.data.dao.local.entity.Item

interface ListingsLocalDao {
    suspend fun getNormalListings(): List<Item>
    suspend fun getPromotedListings(): List<Item>
    suspend fun getFeaturedListings(): List<Item>
    suspend fun save(data: List<Item>)
}