package com.noorifytech.coupon.data.dao.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(@PrimaryKey val id: Int, val content: String, val details: String, val type: String)