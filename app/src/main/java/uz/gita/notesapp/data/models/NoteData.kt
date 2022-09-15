package uz.gita.notesapp.data.models

import java.io.Serializable


data class NoteData(
    var id : Int,
    var title : String,
    var description : String,
    var tag : List<String>,
    var created_time : Long,
    var isPinned : Boolean = false,
    var isDeleted : Boolean = false
) : Serializable