package uz.gita.notesapp.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Int?,
    var title : String?,
    var description : String?,
    var tag : String?,
    var created_time : Long?,
    var isPinned : Boolean = false,
)
