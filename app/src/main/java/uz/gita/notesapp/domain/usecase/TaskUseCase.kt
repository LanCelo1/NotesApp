package uz.gita.notesapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.notesapp.data.local.room.entity.NoteEntity
import uz.gita.notesapp.data.local.room.entity.TaskEntity
import uz.gita.notesapp.data.models.NoteData
import uz.gita.notesapp.data.models.TaskData

interface TaskUseCase {
    fun getAllTask() : Flow<List<TaskData>>
    fun addTask(task : TaskData): Flow<Unit>
    fun editTask(task: TaskData): Flow<Unit>
    fun removeTask(task: TaskData) : Flow<Unit>
    fun searchTask(title : String): Flow<List<TaskData>>
}