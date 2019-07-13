package com.noorifytech.listify.data.mapper

import com.noorifytech.coupon.data.dao.backend.dto.ListingsResponse
import com.noorifytech.coupon.data.vo.Item
import retrofit2.Response

object Mapper {
    fun convert(entity: com.noorifytech.coupon.data.dao.local.entity.Item): Item {
        return Item(entity.id, entity.content, entity.details, entity.type)
    }

    fun convert(dto: com.noorifytech.coupon.data.dao.backend.dto.Item): Item {
        return Item(dto.id, dto.content, dto.details, dto.type)
    }

    fun convert(list: List<com.noorifytech.coupon.data.dao.backend.dto.Item>): List<com.noorifytech.coupon.data.dao.local.entity.Item> {
        return list.map { com.noorifytech.coupon.data.dao.local.entity.Item(it.id, it.content, it.details, it.type) }
    }

    fun convert(response: Response<ListingsResponse<List<com.noorifytech.coupon.data.dao.backend.dto.Item>>>): List<com.noorifytech.coupon.data.dao.backend.dto.Item>? {
        return response.body()?.data
    }
}