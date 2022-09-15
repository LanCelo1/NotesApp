package uz.gita.notesapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.notesapp.domain.usecase.AddNoteUseCase
import uz.gita.notesapp.domain.usecase.NoteUseCase
import uz.gita.notesapp.domain.usecase.TaskUseCase
import uz.gita.notesapp.domain.usecase.impl.AddNoteUseCaseImpl
import uz.gita.notesapp.domain.usecase.impl.NoteUseCaseImpl
import uz.gita.notesapp.domain.usecase.impl.TaskUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun  bindNoteUseCase(noteUseCase: NoteUseCaseImpl) : NoteUseCase

    @Binds
    fun bindAddNoteUseCase(useCase: AddNoteUseCaseImpl) : AddNoteUseCase


    @Binds
    fun bindTaskUseCase(useCase: TaskUseCaseImpl) : TaskUseCase
}