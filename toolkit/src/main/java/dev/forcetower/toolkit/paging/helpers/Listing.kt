package dev.forcetower.toolkit.paging.helpers

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class Listing<T: Any>(
    val pagedList: LiveData<PagedList<T>>,
    val networkState: LiveData<NetworkState>,
    val refreshState: LiveData<NetworkState>,
    val refresh: () -> Unit,
    val retry: () -> Unit
)