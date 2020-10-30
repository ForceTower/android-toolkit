package dev.forcetower.toolkit.extensions

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow


fun <Key : Any, Value : Any> PagingSource<Key, Value>.asPager() : Flow<PagingData<Value>> {
    return Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true
        ),
        pagingSourceFactory = { this }
    ).flow
}