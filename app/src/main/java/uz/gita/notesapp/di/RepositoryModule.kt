package uz.gita.notesapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.notesapp.domain.repository.AppRepository
import uz.gita.notesapp.domain.repository.NoteRepository
import uz.gita.notesapp.domain.repository.TaskRepository
import uz.gita.notesapp.domain.repository.impl.NoteRepositoryImpl
import uz.gita.notesapp.domain.repository.impl.TaskRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindNoteRepository( noteRepository: NoteRepositoryImpl) : NoteRepository
//
//    @Binds
//    fun bindAddNoteRepository(repository: AppRepositoryImpl) : AppRepository

    @Binds
    fun bindTaskRepository(repository: TaskRepositoryImpl) : TaskRepository
}