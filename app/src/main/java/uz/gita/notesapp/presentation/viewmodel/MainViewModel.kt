package uz.gita.notesapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.gita.notesapp.data.models.TypeData

interface MainViewModel {
    val changeFragmentLiveData: LiveData<Int>
    val openAddScreenLiveData: MutableLiveData<Unit>

    fun changeFragmentsByIndex(index: Int)
    fun openAddScreen()

    fun searchText(text: String, type: TypeData)
}