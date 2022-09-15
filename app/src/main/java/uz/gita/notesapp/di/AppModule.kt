package uz.gita.notesapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.notesapp.data.local.room.MyDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun getRoomDatabase(@ApplicationContext context : Context)  =
        Room.databaseBuilder(context,MyDatabase::class.java,"app_database").build()

    @Provides
    @Singleton
    fun getNoteDao(database: MyDatabase) = database.noteDao()

    @Provides
    @Singleton
    fun getTaskDao(database: MyDatabase) = database.taskDao()
}