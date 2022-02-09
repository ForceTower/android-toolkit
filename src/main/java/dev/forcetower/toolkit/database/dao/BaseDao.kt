package dev.forcetower.toolkit.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import androidx.room.Update

abstract class BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(value: T): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertIgnore(value: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(values: List<T>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertAllIgnore(values: List<T>): List<Long>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun update(value: T)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun updateAll(value: List<T>)

    @Delete
    abstract suspend fun delete(values: List<T>)

    @Delete
    abstract suspend fun delete(value: T)

    @Transaction
    open suspend fun insertOrUpdate(values: List<T>) {
        val result = insertAllIgnore(values)
        val update = mutableListOf<T>()
        result.forEachIndexed { index, value ->
            if (value == -1L) update += values[index]
        }
        if (update.isNotEmpty()) updateAll(update)
    }

    @Transaction
    open suspend fun insertOrUpdate(value: T) {
        val result = insertIgnore(value)
        if (result == -1L) update(value)
    }
}
