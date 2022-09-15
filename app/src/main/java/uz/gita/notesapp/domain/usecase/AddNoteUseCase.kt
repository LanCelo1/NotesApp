package uz.gita.notesapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.notesapp.data.local.room.entity.NoteEntity
import uz.gita.notesapp.data.models.NoteData

interface AddNoteUseCase {
    fun addNote(note : NoteData): Flow<Unit>
    fun editNote(note : NoteData) : Flow<Unit>
}