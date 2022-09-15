package uz.gita.notesapp.data.local.room.dao

import androidx.room.*

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data : T)

    @Update
    fun update(data: T)

    @Delete
    fun delete(data : T)


}