package dev.forcetower.toolkit.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import androidx.room.Update

abstract class BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(value: T)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertIgnore(value: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(values: List<T>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertAllIgnore(values: List<T>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun update(value: T)

    @Transaction
    open suspend fun insertOrUpdate(values: List<T>) {
        values.forEach { value ->
            val current = getValueByIDDirect(value)
            if (current != null) {
                if (current != value) update(value)
            } else insert(value)
        }
    }

    @Transaction
    open suspend fun insertOrUpdate(value: T) {
        val current = getValueByIDDirect(value)
        if (current != null) {
            if (current != value) update(value)
        } else insert(value)
    }

    protected open suspend fun getValueByIDDirect(value: T): T? = null
}