package uz.gita.notesapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.notesapp.data.local.room.entity.NoteEntity
import uz.gita.notesapp.data.models.NoteData

interface NoteUseCase {
    fun addNote(note : NoteData): Flow<Unit>
    fun getAllNotes() : Flow<List<NoteData>>
    fun deleteNote(note: NoteData) : Flow<Unit>
    fun searchNote(title : String) : Flow<List<NoteData>>
}