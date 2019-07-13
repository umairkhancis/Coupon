package com.noorifytech.coupon.data.dao.backend.impl.resource

import java.util.*

/**
 * A generic class that describes data with a status
 */
class Resource<T> constructor(val status: Status, var data: T? = null, val message: String? = null, val date: Date? = null) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                status = Status.SUCCESS,
                data = data
            )
        }

        fun <T> error(msg: String?, data: T?): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                msg
            )
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(
                status = Status.LOADING,
                data = data
            )
        }

        fun <T> cached(data: T?, date: Date?): Resource<T> {
            return Resource(
                status = Status.CACHED,
                data = data
            )
        }

        fun <T> reAuthenticate(): Resource<T> {
            return Resource(Status.REAUTH)
        }

        fun <T> logout(): Resource<T> {
            return Resource(Status.LOGOUT)
        }
    }
}