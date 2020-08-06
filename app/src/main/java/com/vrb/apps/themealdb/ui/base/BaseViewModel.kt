package com.vrb.apps.themealdb.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineDispatcher
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    protected fun <T> makeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T) =
        liveData(dispatcher) {
            emit(Result.loading())
            try {
                val result = apiCall()
                emit(Result.success(result))
            } catch (throwable: Throwable) {
                Timber.e(throwable, "Api call error")
                emit(Result.error(null, throwable))
            }
        }

    data class Result<out T>(val status: Status, val data: T?, val error: Throwable?) {
        companion object {
            fun <T> success(data: T): Result<T> {
                return Result(Status.SUCCESS, data, null)
            }

            fun <T> error(data: T? = null, error: Throwable): Result<T> {
                return Result(Status.ERROR, data, error)
            }

            fun <T> loading(data: T? = null): Result<T> {
                return Result(Status.LOADING, data, null)
            }
        }
    }
}
