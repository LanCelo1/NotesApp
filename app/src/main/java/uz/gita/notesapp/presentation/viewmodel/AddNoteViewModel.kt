package uz.gita.notesapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import uz.gita.notesapp.data.models.NoteData

interface AddNoteViewModel {
    val addNoteLiveData: MutableLiveData<Unit>
    val closeScreenLiveData: MutableLiveData<Unit>

    fun addNote(noteData: NoteData)
    fun updateNote(noteData: NoteData)
    // fun deleteNote(noteData: NoteData)
    fun closeScreen()
}