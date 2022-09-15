package uz.gita.notesapp.presentation.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.notesapp.data.models.TaskData
import uz.gita.notesapp.domain.usecase.TaskUseCase
import uz.gita.notesapp.presentation.viewmodel.TaskViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModelImpl @Inject constructor(
    private val useCase: TaskUseCase
) : ViewModel(),TaskViewModel {
    private var isEditable = false
    override val openAddTaskScreenLiveData = MutableLiveData<Unit>()
    override val openEditTaskScreenLiveData = MutableLiveData<TaskData>()
    override val getAllTaskScreenLiveData = MutableLiveData<List<TaskData>>()
    override val addTaskLiveData = MutableLiveData<Unit>()
    override val editTaskLiveData = MutableLiveData<Unit>()
    override val removeTaskLiveData=MutableLiveData<Unit>()
    override val searchTaskLiveData = MutableLiveData<List<TaskData>>()

    override fun openAddTaskScreen() {
        openAddTaskScreenLiveData.value = Unit
    }

    override fun openEditTaskScreen(taskData : TaskData) {
        openEditTaskScreenLiveData.value = taskData
    }

    override fun addTask(message: String) {
        if (message.isEmpty()) return
        useCase.addTask(TaskData(0,message,false)).onEach {
            addTaskLiveData.postValue(Unit)
        }.launchIn(viewModelScope)
    }

    override fun editTask(task: TaskData) {
        useCase.editTask(task).onEach {
            editTaskLiveData.postValue(Unit)
        }.launchIn(viewModelScope)
    }

    override fun removeTask(task: TaskData) {
        useCase.removeTask(task).onEach {
            removeTaskLiveData.postValue(Unit)
        }.launchIn(viewModelScope)
    }

    override fun getAllTask()  {
        useCase.getAllTask().onEach {
            getAllTaskScreenLiveData.postValue(it)
        }.launchIn(viewModelScope)
    }

    override fun searchTask(title: String) {
        useCase.searchTask(title).onEach {
            searchTaskLiveData.value = it
        }.launchIn(viewModelScope)
    }
    override fun removeItemBackground(task : TaskData){
        useCase.removeTask(task).onEach {

        }.launchIn(viewModelScope)
    }
}