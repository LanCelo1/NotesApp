package uz.gita.notesapp.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import uz.gita.notesapp.data.models.NoteData
import uz.gita.notesapp.domain.usecase.AddNoteUseCase
import uz.gita.notesapp.domain.usecase.NoteUseCase
import uz.gita.notesapp.presentation.viewmodel.AddNoteViewModel
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModelImpl @Inject constructor(
    private val useCase: AddNoteUseCase
) : ViewModel(), AddNoteViewModel {
    override val addNoteLiveData = MutableLiveData<Unit>()
    override val closeScreenLiveData= MutableLiveData<Unit>()

    override fun updateNote(noteData: NoteData) {
        useCase.editNote(noteData).onEach {

        }.launchIn(viewModelScope)
        closeScreenLiveData.value = Unit
    }
    override fun addNote(noteData: NoteData) {
        useCase.addNote(noteData).onEach {

        }.launchIn(viewModelScope)
        closeScreenLiveData.value = Unit
    }

    override fun closeScreen() {
        closeScreenLiveData.value = Unit
    }
}