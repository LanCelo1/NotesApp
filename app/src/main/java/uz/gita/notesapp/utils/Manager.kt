package uz.gita.notesapp.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.gita.notesapp.data.models.NoteData
import uz.gita.notesapp.data.models.TaskData

object Manager {
    var _searchNoteLivedata =MutableLiveData<List<NoteData>>()
    val searchNoteLivedata : LiveData<List<NoteData>> get()  = _searchNoteLivedata
    var _searchTaskLivedata = MutableLiveData<List<TaskData>>()
    val searchTaskLivedata : LiveData<List<TaskData>> get() = _searchTaskLivedata
}