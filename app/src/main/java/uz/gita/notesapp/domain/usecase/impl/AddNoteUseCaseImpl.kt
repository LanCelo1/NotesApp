package uz.gita.notesapp.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.notesapp.data.local.room.dao.NoteDao
import uz.gita.notesapp.data.local.room.entity.NoteEntity
import uz.gita.notesapp.data.models.NoteData
import uz.gita.notesapp.domain.repository.NoteRepository
import uz.gita.notesapp.domain.usecase.AddNoteUseCase
import uz.gita.notesapp.domain.usecase.NoteUseCase
import uz.gita.notesapp.utils.getNoteData
import javax.inject.Inject

class AddNoteUseCaseImpl @Inject constructor(
    private var repository: NoteRepository
) : AddNoteUseCase {
    override fun addNote(note: NoteData) : Flow<Unit> = flow<Unit> {
        var s = ""
        note.tag.forEach {
            s += it
        }
        val note = NoteEntity(null,note.title,note.description,s,note.created_time,note.isPinned)
        repository.addNote(note)
    }.flowOn(Dispatchers.IO)

    override fun editNote(note: NoteData): Flow<Unit> = flow<Unit> {
        var s = ""
        note.tag.forEach {
            s += it
        }
        val note = NoteEntity(note.id,note.title,note.description,s,note.created_time,note.isPinned)
        repository.updateNote(note)
    }.flowOn(Dispatchers.IO)
}