package uz.gita.notesapp.presentation.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import uz.gita.notesapp.data.models.NoteData
import uz.gita.notesapp.domain.usecase.NoteUseCase
import uz.gita.notesapp.presentation.viewmodel.NoteViewModel
import javax.inject.Inject

@HiltViewModel
class NoteViewModelImpl @Inject constructor(
    private var useCase: NoteUseCase,
) : ViewModel(), NoteViewModel {
    override val getAllNotesLiveData = MutableLiveData<List<NoteData>>()
    override val openEditNotesLiveData = MutableLiveData<NoteData>()
    override val openAddScreenLiveData = MutableLiveData<Unit>()

    override val addNoteLiveData = MutableLiveData<Unit>()


    override fun getAllNotes() {
        useCase.getAllNotes().onEach {
            getAllNotesLiveData.postValue(it)
        }.launchIn(viewModelScope)
    }

    override val deleteNoteLiveData = MutableLiveData<Unit>()

    override fun deleteNote(note: NoteData) {
        useCase.deleteNote(note).onEach {
            deleteNoteLiveData.postValue(Unit)
        }.launchIn(viewModelScope)
    }
    override fun openEditNoteScreen(note: NoteData) {
        openEditNotesLiveData.value = note
    }

    override fun openAddScreen() {
        openAddScreenLiveData.value = Unit
    }

    override fun searchNote(title: String) {
        useCase.searchNote(title).onEach {
            searchNoteLiveData.value = it
        }.launchIn(viewModelScope)
    }

    override val searchNoteLiveData = MutableLiveData<List<NoteData>>()
}