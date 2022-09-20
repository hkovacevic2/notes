package ba.etf.unsa.dipl.notes.feature_note.data.repository

import ba.etf.unsa.dipl.notes.feature_note.data.source.NoteDAO
import ba.etf.unsa.dipl.notes.feature_note.domain.model.Note
import ba.etf.unsa.dipl.notes.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDAO
): NoteRepository {
    override fun getAll(): Flow<List<Note>> = dao.getAll()

    override suspend fun getById(id: Int): Note? = dao.getById(id)

    override suspend fun insert(note: Note) = dao.insert(note)

    override suspend fun delete(note: Note) = dao.delete(note)
}