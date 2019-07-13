package com.noorifytech.coupon.factory

import com.noorifytech.coupon.application.CouponApplication
import com.noorifytech.coupon.data.dao.backend.impl.ListingsBackendDaoImpl
import com.noorifytech.coupon.data.dao.backend.impl.retrofit.ListingsServiceImpl
import com.noorifytech.coupon.data.dao.local.impl.ListingsLocalDaoImpl
import com.noorifytech.coupon.data.dao.local.impl.room.AppDatabase
import com.noorifytech.listify.data.repository.ListingsRepository
import com.noorifytech.listify.data.repository.impl.ListingsRepositoryImpl

object Coupon {

    // TODO: Implement service locator pattern for Dependency Injection
    fun get(): ListingsRepository {

        // Local DB
        val appDatabase = AppDatabase.getDatabase(CouponApplication.instance.applicationContext)
        val listingsLocalDao = ListingsLocalDaoImpl(appDatabase)

        // Backend
//        TODO: uncomment when retrofit is integrated.
//        val listingsService = RetrofitUtil.defaultRetrofit.create(ListingsService::class.java)
        val listingsService = ListingsServiceImpl()
        val listingsBackendDao = ListingsBackendDaoImpl(listingsService)

        return ListingsRepositoryImpl(listingsLocalDao, listingsBackendDao)
    }
}