package ba.etf.unsa.dipl.notes.di

import android.app.Application
import androidx.room.Room
import ba.etf.unsa.dipl.notes.feature_note.data.repository.NoteRepositoryImpl
import ba.etf.unsa.dipl.notes.feature_note.data.source.NoteDB
import ba.etf.unsa.dipl.notes.feature_note.domain.repository.NoteRepository
import ba.etf.unsa.dipl.notes.feature_note.domain.usecase.*
import ba.etf.unsa.dipl.notes.feature_note.domain.util.ResourcesProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDB(app: Application) =
         Room.databaseBuilder(
            app,
            NoteDB::class.java,
            NoteDB.NAME
        ).build()


    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDB): NoteRepository = NoteRepositoryImpl(db.noteDao)

    @Provides
    @Singleton
    fun provideResourcesProvider(app: Application) = ResourcesProvider(app.applicationContext)

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            addNoteUseCase = AddNoteUseCase(repository),
            getNoteUseCase = GetNoteUseCase(repository)
        )
    }
}