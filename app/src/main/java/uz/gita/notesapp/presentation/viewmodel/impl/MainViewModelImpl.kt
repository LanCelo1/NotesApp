package uz.gita.notesapp.presentation.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.notesapp.data.models.TypeData
import uz.gita.notesapp.domain.usecase.NoteUseCase
import uz.gita.notesapp.domain.usecase.TaskUseCase
import uz.gita.notesapp.presentation.viewmodel.MainViewModel
import uz.gita.notesapp.utils.Manager
import javax.inject.Inject

@HiltViewModel
class MainViewModelImpl @Inject constructor(
    private val noteUseCase: NoteUseCase,
    private val taskUseCase: TaskUseCase
) : ViewModel(), MainViewModel {
    private var count = -1
    override val changeFragmentLiveData = MutableLiveData<Int>()
    override val openAddScreenLiveData = MutableLiveData<Unit>()

    override fun changeFragmentsByIndex(index: Int) {
        if (count == index) return
        count = index
        changeFragmentLiveData.value = index
    }

    override fun openAddScreen() {
        openAddScreenLiveData.value = Unit
    }

    override fun searchText(text: String,type: TypeData){
        when(type.index){
            0-> {
                noteUseCase.searchNote(text).onEach {
                    Manager._searchNoteLivedata.value = it
                }.launchIn(viewModelScope)
            }
            1->{
                taskUseCase.searchTask(text).onEach {
                    Manager._searchTaskLivedata.value = it
                }.launchIn(viewModelScope)
            }
        }
    }

}