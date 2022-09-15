package uz.gita.notesapp.domain.repository

import uz.gita.notesapp.data.local.room.entity.NoteEntity
import uz.gita.notesapp.data.models.NoteData


interface NoteRepository {
    suspend fun addNote(noteData : NoteEntity)
    suspend fun updateNote(noteData : NoteEntity)
    suspend fun getAllNotes() : List<NoteEntity>
    suspend fun deleteNote(note : NoteEntity)
    suspend fun searchNote(title : String) : List<NoteEntity>
}