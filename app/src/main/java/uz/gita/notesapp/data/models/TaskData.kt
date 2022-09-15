package uz.gita.notesapp.data.models

import androidx.room.PrimaryKey

data class TaskData(
    var id: Int?,
    var title: String?,
    var isChecked: Boolean = false,
)