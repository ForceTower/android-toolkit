package dev.forcetower.toolkit.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import dev.forcetower.toolkit.paging.helpers.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class SuspendableDataSource<Key, Value>(
    private val scope: CoroutineScope,
    private val error: (Throwable) -> Unit
) : PageKeyedDataSource<Key, Value>() {
    private var retry: (() -> Any)? = null
    val networkState = MutableLiveData<NetworkState>()
    val initialLoad = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.invoke()
    }

    override fun loadInitial(
        params: LoadInitialParams<Key>,
        callback: LoadInitialCallback<Key, Value>
    ) {
        scope.launch {
            try {
                networkState.postValue(NetworkState.LOADING)
                suspendLoadInitial(params, callback)
                retry = null
                networkState.postValue(NetworkState.LOADED)
                initialLoad.postValue(NetworkState.LOADED)
            } catch (error: Throwable) {
                error(error)
                retry = { loadInitial(params, callback) }
                Timber.e(error, "Error on data source")
                val value = NetworkState.error(error.message ?: "unknown error")
                networkState.postValue(value)
                initialLoad.postValue(value)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Key>, callback: LoadCallback<Key, Value>) {
        scope.launch {
            try {
                networkState.postValue(NetworkState.LOADING)
                suspendLoadAfter(params, callback)
                retry = null
                networkState.postValue(NetworkState.LOADED)
            } catch (error: Throwable) {
                error(error)
                retry = { loadAfter(params, callback) }
                Timber.e(error, "Error on data source")
                networkState.postValue(NetworkState.error(error.message ?: "unknown err"))
            }
        }
    }

    override fun loadBefore(params: LoadParams<Key>, callback: LoadCallback<Key, Value>) {
        scope.launch {
            try {
                networkState.postValue(NetworkState.LOADING)
                suspendLoadBefore(params, callback)
                retry = null
                networkState.postValue(NetworkState.LOADED)
            } catch (error: Throwable) {
                error(error)
                retry = { loadBefore(params, callback) }
                Timber.e(error, "Error on data source")
                networkState.postValue(NetworkState.error(error.message ?: "unknown err"))
            }
        }
    }

    abstract suspend fun suspendLoadInitial(
        params: LoadInitialParams<Key>,
        callback: LoadInitialCallback<Key, Value>
    )

    abstract suspend fun suspendLoadAfter(
        params: LoadParams<Key>,
        callback: LoadCallback<Key, Value>
    )

    abstract suspend fun suspendLoadBefore(
        params: LoadParams<Key>,
        callback: LoadCallback<Key, Value>
    )
}