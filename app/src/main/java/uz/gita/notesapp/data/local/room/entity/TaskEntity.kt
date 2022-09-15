package uz.gita.notesapp.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var title: String?,
    var isChecked: Boolean = false,
)