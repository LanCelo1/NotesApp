package uz.gita.notesapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.gita.notesapp.data.models.NoteData

interface NoteViewModel {
    val getAllNotesLiveData : LiveData<List<NoteData>>
    val addNoteLiveData : LiveData<Unit>
    val openEditNotesLiveData: LiveData<NoteData>
    val openAddScreenLiveData: LiveData<Unit>
    val deleteNoteLiveData: MutableLiveData<Unit>
    val searchNoteLiveData: MutableLiveData<List<NoteData>>


    fun getAllNotes()
    fun openEditNoteScreen(note: NoteData)
    fun openAddScreen()
    fun searchNote(title : String)
    fun deleteNote(note : NoteData)
}