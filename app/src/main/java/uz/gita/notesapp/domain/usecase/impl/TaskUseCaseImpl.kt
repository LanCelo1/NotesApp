package uz.gita.notesapp.domain.usecase.impl

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import uz.gita.notesapp.data.local.room.entity.NoteEntity
import uz.gita.notesapp.data.local.room.entity.TaskEntity
import uz.gita.notesapp.data.models.NoteData
import uz.gita.notesapp.data.models.TaskData
import uz.gita.notesapp.domain.repository.TaskRepository
import uz.gita.notesapp.domain.usecase.TaskUseCase
import uz.gita.notesapp.utils.getTaskData
import javax.inject.Inject

class TaskUseCaseImpl @Inject constructor(
    private var repository: TaskRepository,
) : TaskUseCase {
    override fun getAllTask(): Flow<List<TaskData>> = flow<List<TaskData>> {
        val list = ArrayList<TaskData>()
        repository.getALlTask().map {
            list.add(TaskData(it.id, it.title, it.isChecked))
        }
        emit(list)
    }.flowOn(Dispatchers.IO)

    override fun addTask(task: TaskData): Flow<Unit> = flow {
        val taskEntity = TaskEntity(null, task.title, task.isChecked)
        emit(repository.addTask(taskEntity))
    }.flowOn(Dispatchers.IO)

    override fun editTask(task: TaskData): Flow<Unit> = flow {
        val taskEntity = TaskEntity(task.id, task.title, task.isChecked)
        emit(repository.editTask(taskEntity))
    }.flowOn(Dispatchers.IO)

    override fun removeTask(task: TaskData): Flow<Unit> = flow<Unit> {
        val taskEntity = TaskEntity(task.id, task.title, task.isChecked)
        emit(repository.removeTask(taskEntity))
    }.flowOn(Dispatchers.IO)

    override fun searchTask(title: String) : Flow<List<TaskData>> = flow<List<TaskData>> {
        val list = repository.searchTask(title).map{
            it.getTaskData()
        }
        emit(list)
    }.flowOn(Dispatchers.IO)
}