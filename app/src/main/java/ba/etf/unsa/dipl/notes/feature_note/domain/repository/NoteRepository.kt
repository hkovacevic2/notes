package ba.etf.unsa.dipl.notes.feature_note.domain.repository

import ba.etf.unsa.dipl.notes.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAll(): Flow<List<Note>>

    suspend fun getById(id: Int): Note?

    suspend fun insert(note: Note)

    suspend fun delete(note: Note)
}