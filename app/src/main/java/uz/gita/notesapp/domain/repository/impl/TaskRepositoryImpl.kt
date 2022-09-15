package uz.gita.notesapp.domain.repository.impl

import uz.gita.notesapp.data.local.room.dao.TaskDao
import uz.gita.notesapp.data.local.room.entity.TaskEntity
import uz.gita.notesapp.data.models.TaskData
import uz.gita.notesapp.domain.repository.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private var taskDao: TaskDao,
) : TaskRepository {
    override suspend fun addTask(task: TaskEntity) = taskDao.insert(task)

    override suspend fun getALlTask(): List<TaskEntity> {
        return taskDao.getAllNotes()
    }

    override suspend fun editTask(task: TaskEntity) {
        return taskDao.update(task)
    }

    override suspend fun removeTask(task: TaskEntity) {
        return taskDao.delete(task)
    }

    override suspend fun searchTask(title:String)
     = taskDao.searchTask(title)
}