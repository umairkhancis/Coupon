package com.noorifytech.coupon.data.dao.backend.impl.retrofit

import com.noorifytech.coupon.data.dao.backend.dto.Item
import com.noorifytech.coupon.data.dao.backend.dto.ListingsResponse
import com.noorifytech.coupon.data.dao.backend.impl.resource.ApiResponse
import kotlinx.coroutines.delay
import retrofit2.Response

/**
 * dummy implementation for retrofit
 */
class ListingsServiceImpl: ListingsService {

    override suspend fun getNormalListings(): ApiResponse<ListingsResponse<List<Item>>> {
        delay(DELAY)
        return ApiResponse.create(Response.success(ListingsResponse(normalListings(), 200, "success")))
    }


    override suspend fun gePromotedListings(): ApiResponse<ListingsResponse<List<Item>>> {
        delay(DELAY)
        return ApiResponse.create(Response.success(ListingsResponse(promotedListings(), 200, "success")))
    }

    override suspend fun getFeaturedListings(): ApiResponse<ListingsResponse<List<Item>>> {
        delay(DELAY)
        return ApiResponse.create(Response.success(ListingsResponse(featuredListings(), 200, "success")))
    }

    private fun normalListings(): List<Item> {
        val normalListings = mutableListOf<Item>()
        normalListings.add(Item(1, "Normal Listing 1", "Normal Listing Details", "normal"))
        normalListings.add(Item(2, "Normal Listing 2", "Normal Listing Details", "normal"))
        normalListings.add(Item(3, "Normal Listing 3", "Normal Listing Details", "normal"))

        return normalListings
    }

    private fun promotedListings(): List<Item> {
        val promoted = mutableListOf<Item>()
        promoted.add(Item(4, "Promoted Listing 1", "Promoted Listing Details", "promoted"))
        promoted.add(Item(5, "Promoted Listing 2", "Promoted Listing Details", "promoted"))
        promoted.add(Item(6, "Promoted Listing 3", "Promoted Listing Details", "promoted"))

        return promoted
    }

    private fun featuredListings(): List<Item> {
        val featuredListings = mutableListOf<Item>()
        featuredListings.add(Item(7, "Featured Listing 1", "Featured Listing Details", "featured"))
        featuredListings.add(Item(8, "Featured Listing 2", "Featured Listing Details", "featured"))
        featuredListings.add(Item(9, "Featured Listing 3", "Featured Listing Details", "featured"))

        return featuredListings
    }

    companion object {
        const val DELAY = 2000L
    }
}