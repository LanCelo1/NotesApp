package uz.gita.notesapp.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.gita.notesapp.data.local.room.dao.NoteDao
import uz.gita.notesapp.data.local.room.dao.TaskDao
import uz.gita.notesapp.data.local.room.entity.NoteEntity
import uz.gita.notesapp.data.local.room.entity.TaskEntity

@Database(entities = [NoteEntity::class,TaskEntity::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun noteDao() : NoteDao
    abstract fun taskDao() : TaskDao
}