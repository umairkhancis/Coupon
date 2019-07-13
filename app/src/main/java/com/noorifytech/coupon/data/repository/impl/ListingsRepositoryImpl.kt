package com.noorifytech.listify.data.repository.impl

import androidx.lifecycle.LiveData
import com.noorifytech.coupon.data.dao.backend.ListingsBackendDao
import com.noorifytech.coupon.data.dao.backend.dto.ListingsResponse
import com.noorifytech.coupon.data.dao.backend.impl.resource.ApiResponse
import com.noorifytech.coupon.data.dao.backend.impl.resource.ApiSuccessResponse
import com.noorifytech.coupon.data.dao.backend.impl.resource.NetworkBoundResource
import com.noorifytech.coupon.data.dao.backend.impl.resource.Resource
import com.noorifytech.coupon.data.dao.local.ListingsLocalDao
import com.noorifytech.listify.data.mapper.Mapper
import com.noorifytech.listify.data.repository.ListingsRepository
import com.noorifytech.coupon.data.vo.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.Response

class ListingsRepositoryImpl(
    private val listingsLocalDao: ListingsLocalDao,
    private val listingsBackendDao: ListingsBackendDao
) : ListingsRepository {

    override suspend fun getListings(): LiveData<Resource<List<Item>>> {
        return object :
            NetworkBoundResource<List<Item>, ListingsResponse<List<com.noorifytech.coupon.data.dao.backend.dto.Item>>>() {

            override suspend fun saveCallResult(response: ListingsResponse<List<com.noorifytech.coupon.data.dao.backend.dto.Item>>) {
                withContext(Dispatchers.IO) { listingsLocalDao.save(Mapper.convert(response.data)) }
            }

            override fun shouldFetch(data: List<Item>?) = data == null || data.isEmpty()

            override suspend fun loadFromDb(): List<Item> {
                val startTime = System.currentTimeMillis()

                val normalListingsResult = GlobalScope.async(Dispatchers.IO) { listingsLocalDao.getNormalListings() }
                val promotedListingsResult = GlobalScope.async(Dispatchers.IO) { listingsLocalDao.getPromotedListings() }
                val featuredListingsResult = GlobalScope.async(Dispatchers.IO) { listingsLocalDao.getFeaturedListings() }

                val combinedListings = combineListings(normalListingsResult.await(), promotedListingsResult.await(), featuredListingsResult.await()).map { Mapper.convert(it) }

                val endTime = System.currentTimeMillis()
                println("Time Take by loadFromDb: ${(endTime - startTime) / 1000} seconds")

                return combinedListings
            }

            override suspend fun apiCall(): ApiResponse<ListingsResponse<List<com.noorifytech.coupon.data.dao.backend.dto.Item>>> {
                val startTime = System.currentTimeMillis()

                val normalListingsResult = GlobalScope.async(Dispatchers.IO) { listingsBackendDao.getNormalListings() }
                val promotedListingsResult =
                    GlobalScope.async(Dispatchers.IO) { listingsBackendDao.getPromotedListings() }
                val featuredListingsResult =
                    GlobalScope.async(Dispatchers.IO) { listingsBackendDao.getFeaturedListings() }
                val combinedListings = combineListings(
                    normalListingsResult.await(),
                    promotedListingsResult.await(),
                    featuredListingsResult.await()
                )

                val endTime = System.currentTimeMillis()
                println("Time Take by apiCall: ${(endTime - startTime) / 1000} seconds")

                return combinedListings
            }
        }.create().asLiveData()
    }

    override suspend fun getNormalListings(): LiveData<Resource<List<Item>>> {
        return object :
            NetworkBoundResource<List<Item>, ListingsResponse<List<com.noorifytech.coupon.data.dao.backend.dto.Item>>>() {

            override suspend fun saveCallResult(response: ListingsResponse<List<com.noorifytech.coupon.data.dao.backend.dto.Item>>) {
                listingsLocalDao.save(Mapper.convert(response.data))
            }

            override fun shouldFetch(data: List<Item>?) = data == null

            override suspend fun loadFromDb(): List<Item> {
                return listingsLocalDao.getNormalListings().map { Mapper.convert(it) }
            }

            override suspend fun apiCall(): ApiResponse<ListingsResponse<List<com.noorifytech.coupon.data.dao.backend.dto.Item>>> {
                return listingsBackendDao.getNormalListings()
            }
        }.create().asLiveData()
    }

    override suspend fun getPromotedListings(): LiveData<Resource<List<Item>>> {
        return object :
            NetworkBoundResource<List<Item>, ListingsResponse<List<com.noorifytech.coupon.data.dao.backend.dto.Item>>>() {

            override suspend fun saveCallResult(response: ListingsResponse<List<com.noorifytech.coupon.data.dao.backend.dto.Item>>) {
                listingsLocalDao.save(Mapper.convert(response.data))
            }

            override fun shouldFetch(data: List<Item>?) = data == null

            override suspend fun loadFromDb(): List<Item> {
                return listingsLocalDao.getPromotedListings().map { Mapper.convert(it) }
            }

            override suspend fun apiCall(): ApiResponse<ListingsResponse<List<com.noorifytech.coupon.data.dao.backend.dto.Item>>> {
                return listingsBackendDao.getPromotedListings()
            }
        }.create().asLiveData()
    }

    override suspend fun getFeaturedListings(): LiveData<Resource<List<Item>>> {
        return object :
            NetworkBoundResource<List<Item>, ListingsResponse<List<com.noorifytech.coupon.data.dao.backend.dto.Item>>>() {

            override suspend fun saveCallResult(response: ListingsResponse<List<com.noorifytech.coupon.data.dao.backend.dto.Item>>) {
                listingsLocalDao.save(Mapper.convert(response.data))
            }

            override fun shouldFetch(data: List<Item>?) = data == null

            override suspend fun loadFromDb(): List<Item> {
                return listingsLocalDao.getFeaturedListings().map { Mapper.convert(it) }
            }

            override suspend fun apiCall(): ApiResponse<ListingsResponse<List<com.noorifytech.coupon.data.dao.backend.dto.Item>>> {
                return listingsBackendDao.getFeaturedListings()
            }
        }.create().asLiveData()
    }

    private fun combineListings(
        normalListingsResponse: ApiResponse<ListingsResponse<List<com.noorifytech.coupon.data.dao.backend.dto.Item>>>,
        promotedListingsResponse: ApiResponse<ListingsResponse<List<com.noorifytech.coupon.data.dao.backend.dto.Item>>>,
        featuredListingsResponse: ApiResponse<ListingsResponse<List<com.noorifytech.coupon.data.dao.backend.dto.Item>>>
    ): ApiResponse<ListingsResponse<List<com.noorifytech.coupon.data.dao.backend.dto.Item>>> {

        val combinedListings = mutableListOf<com.noorifytech.coupon.data.dao.backend.dto.Item>()

        combinedListings.addAll((featuredListingsResponse as ApiSuccessResponse).body.data)
        combinedListings.addAll((promotedListingsResponse as ApiSuccessResponse).body.data)
        combinedListings.addAll((normalListingsResponse as ApiSuccessResponse).body.data)

        return ApiResponse.create(
            Response.success(
                ListingsResponse(
                    combinedListings as List<com.noorifytech.coupon.data.dao.backend.dto.Item>,
                    200,
                    "combined data"
                )
            )
        )
    }

    private fun combineListings(
        normalListings: List<com.noorifytech.coupon.data.dao.local.entity.Item>,
        promotedListings: List<com.noorifytech.coupon.data.dao.local.entity.Item>,
        featuredListings: List<com.noorifytech.coupon.data.dao.local.entity.Item>
    ): List<com.noorifytech.coupon.data.dao.local.entity.Item> {

        val combinedListings = mutableListOf<com.noorifytech.coupon.data.dao.local.entity.Item>()

        combinedListings.addAll(featuredListings)
        combinedListings.addAll(promotedListings)
        combinedListings.addAll(normalListings)

        return combinedListings
    }
}