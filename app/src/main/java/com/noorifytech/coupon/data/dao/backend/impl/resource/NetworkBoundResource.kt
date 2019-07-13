package com.noorifytech.coupon.data.dao.backend.impl.resource

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result = MediatorLiveData<Resource<ResultType>>()

    suspend fun create(): NetworkBoundResource<ResultType, RequestType> {
        setValue(Resource.loading(null))
        val localDbResult = loadFromDb()
        if (shouldFetch(localDbResult)) {
            fetchFromNetwork(localDbResult)
        } else {
            setValue(Resource.success(localDbResult))
        }

        return this
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private suspend fun fetchFromNetwork(localDbResult: ResultType) {
        val apiResponse = apiCall()

        // we re-attach localDbResult as a new source, it will dispatch its latest value quickly
        setValue(Resource.loading(localDbResult))

        when (apiResponse) {
            is ApiSuccessResponse -> {
                // TODO: io thread
                saveCallResult(processResponse(apiResponse))

                // we specially request a new live data,
                // otherwise we will get immediately last cached value,
                // which may not be updated with latest results received from network.
                val newLocalDbResult = loadFromDb()
                setValue(Resource.success(newLocalDbResult))
            }
            is ApiEmptyResponse -> {
                // reload from disk whatever we had
                setValue(Resource.success(loadFromDb()))
            }
            is ApiErrorResponse -> {
                onFetchFailed()
                setValue(Resource.error(apiResponse.errorMessage, localDbResult))
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    protected abstract suspend fun saveCallResult(response: RequestType)

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun loadFromDb(): ResultType

    protected abstract suspend fun apiCall(): ApiResponse<RequestType>
}