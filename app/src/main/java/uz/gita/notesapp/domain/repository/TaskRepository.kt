package uz.gita.notesapp.domain.repository

import uz.gita.notesapp.data.local.room.entity.TaskEntity
import uz.gita.notesapp.data.models.TaskData

interface TaskRepository {
    suspend fun addTask(task : TaskEntity)
    suspend fun getALlTask() : List<TaskEntity>
    suspend fun editTask(task : TaskEntity)
    suspend fun removeTask(task: TaskEntity)
    suspend fun searchTask(title: String) : List<TaskEntity>
}