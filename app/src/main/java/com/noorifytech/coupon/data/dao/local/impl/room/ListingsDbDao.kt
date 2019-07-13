package com.noorifytech.coupon.data.dao.local.impl.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.noorifytech.coupon.data.dao.local.entity.Item

@Dao
interface ListingsDbDao {
    @Query("Select * from Item where type like '%normal'")
    fun getNormalListings(): List<Item>

    @Query("Select * from Item where type like '%promoted'")
    fun gePromotedListings(): List<Item>

    @Query("Select * from Item where type like '%featured'")
    fun geFeaturedListings(): List<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(data: List<Item>)
}