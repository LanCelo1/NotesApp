package uz.gita.notesapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import uz.gita.notesapp.data.local.room.entity.NoteEntity

@Dao
interface NoteDao : BaseDao<NoteEntity>{

    @Query("SELECT * FROM notes")
    fun getAllNotes() : List<NoteEntity>

    @Query("SElECT * FROM notes WHERE  title LIKE  :t || '%'")
    fun searchNote(t : String) : List<NoteEntity>
}