package uz.gita.notesapp.domain.repository.impl

import uz.gita.notesapp.data.local.room.dao.NoteDao
import uz.gita.notesapp.data.local.room.entity.NoteEntity
import uz.gita.notesapp.data.models.NoteData
import uz.gita.notesapp.domain.repository.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {
    override suspend fun addNote(noteData: NoteEntity)  {
        return noteDao.insert(noteData)
    }

    override suspend fun updateNote(noteData: NoteEntity) {
        return noteDao.update(noteData)
    }

    override suspend fun getAllNotes(): List<NoteEntity> {
        return noteDao.getAllNotes()
    }

    override suspend fun deleteNote(note: NoteEntity)
    {
        return noteDao.delete(note)
    }

    override suspend fun searchNote(title: String) : List<NoteEntity> {
        return noteDao.searchNote(title)
    }
}