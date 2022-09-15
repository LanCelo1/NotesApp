package uz.gita.notesapp.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.notesapp.data.local.room.dao.NoteDao
import uz.gita.notesapp.data.local.room.entity.NoteEntity
import uz.gita.notesapp.data.models.NoteData
import uz.gita.notesapp.domain.repository.NoteRepository
import uz.gita.notesapp.domain.usecase.NoteUseCase
import uz.gita.notesapp.utils.getNoteData
import javax.inject.Inject

class NoteUseCaseImpl @Inject constructor(
    private var repository: NoteRepository,
) : NoteUseCase {
    override fun addNote(noteData: NoteData): Flow<Unit> = flow<Unit> {
        var s = ""
        noteData.tag.forEach {
            s += it
        }
        val note = NoteEntity(null,
            noteData.title,
            noteData.description,
            s,
            noteData.created_time,
            noteData.isPinned)
        emit(repository.addNote(note))
    }.flowOn(Dispatchers.IO)


    override fun getAllNotes(): Flow<List<NoteData>> = flow {
        val list1 = repository.getAllNotes().map {
            val list = it.tag!!.split("")
            it.getNoteData(list)
        }
        emit(list1)
    }.flowOn(Dispatchers.IO)

    override fun deleteNote(noteData: NoteData): Flow<Unit> = flow {
        var s = ""
        noteData.tag.forEach { s += it }
        emit(repository.deleteNote(NoteEntity(noteData.id,
            noteData.title,
            noteData.description,
            s,
            noteData.created_time,
            noteData.isPinned)))
    }.flowOn(Dispatchers.IO)

    override fun searchNote(title: String): Flow<List<NoteData>> = flow<List<NoteData>> {
        val list1 = repository.searchNote(title).map {
            val list = it.tag!!.split("")
            it.getNoteData(list)
        }
        emit(list1)
    }.flowOn(Dispatchers.IO)
}