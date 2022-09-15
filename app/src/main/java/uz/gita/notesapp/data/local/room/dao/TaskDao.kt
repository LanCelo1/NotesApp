package uz.gita.notesapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import uz.gita.notesapp.data.local.room.entity.NoteEntity
import uz.gita.notesapp.data.local.room.entity.TaskEntity

@Dao
interface TaskDao : BaseDao<TaskEntity>{

    @Query("SELECT * FROM tasks")
    fun getAllNotes() : List<TaskEntity>

    @Query("SElECT * FROM tasks WHERE  title LIKE :title || '%'")
    fun searchTask(title : String) : List<TaskEntity>
}