package com.noorifytech.coupon.application

import android.app.Application

class CouponApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        instance = this
    }

    companion object {
        lateinit var instance: CouponApplication
    }
}