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

    enum class DataAccessStrategy {
        LOCAL,
        REMOTE
    }

    protected fun <T> performGetOperation(
        dispatcher: CoroutineDispatcher,
        accessStrategy: DataAccessStrategy,
        localCall: (suspend () -> T)? = null,
        apiCall: (suspend () -> T)? = null
    ) =
        liveData(dispatcher) {
            when (accessStrategy) {
                DataAccessStrategy.LOCAL -> {
                    emit(Result.success(localCall?.invoke()))
                }

                DataAccessStrategy.REMOTE -> {
                    emit(Result.loading())
                    try {
                        val result = apiCall?.invoke()
                        emit(Result.success(result))
                    } catch (throwable: Throwable) {
                        Timber.e(throwable, "Api call error")
                        emit(Result.error(null, throwable))
                    }
                }
            }
        }

    data class GetOperation<out T>(val accessStrategy: DataAccessStrategy, val localCall: (suspend () -> T)? = null, val apiCall: (suspend () -> T)? = null)

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
