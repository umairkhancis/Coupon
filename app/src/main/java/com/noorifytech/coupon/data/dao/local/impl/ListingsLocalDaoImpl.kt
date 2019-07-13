package com.noorifytech.coupon.data.dao.local.impl

import com.noorifytech.coupon.data.dao.local.ListingsLocalDao
import com.noorifytech.coupon.data.dao.local.entity.Item
import com.noorifytech.coupon.data.dao.local.impl.room.AppDatabase

class ListingsLocalDaoImpl(private val database: AppDatabase) : ListingsLocalDao {
    override suspend fun getNormalListings(): List<Item> = database.listingsDao().getNormalListings()

    override suspend fun getPromotedListings(): List<Item> = database.listingsDao().gePromotedListings()

    override suspend fun getFeaturedListings(): List<Item> = database.listingsDao().geFeaturedListings()

    override suspend fun save(data: List<Item>) {
        database.listingsDao().save(data)
    }
}