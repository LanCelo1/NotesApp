package uz.gita.notesapp.presentation.viewmodel

import android.app.TaskStackBuilder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import uz.gita.notesapp.data.models.TaskData

interface TaskViewModel {
    val openAddTaskScreenLiveData: LiveData<Unit>
    val addTaskLiveData: LiveData<Unit>
    val editTaskLiveData: LiveData<Unit>
    val getAllTaskScreenLiveData: LiveData<List<TaskData>>
    val openEditTaskScreenLiveData: LiveData<TaskData>
    val removeTaskLiveData: LiveData<Unit>
    val searchTaskLiveData : LiveData<List<TaskData>>

    fun openAddTaskScreen()
    fun openEditTaskScreen(task: TaskData)
    fun addTask(message : String)
    fun getAllTask()
    fun editTask(task: TaskData)
    fun removeTask(task : TaskData)
    fun searchTask(title : String)
    fun removeItemBackground(task: TaskData)
}