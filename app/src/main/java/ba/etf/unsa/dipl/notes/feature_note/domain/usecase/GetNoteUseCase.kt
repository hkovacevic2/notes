package ba.etf.unsa.dipl.notes.feature_note.domain.usecase

import ba.etf.unsa.dipl.notes.feature_note.domain.model.Note
import ba.etf.unsa.dipl.notes.feature_note.domain.repository.NoteRepository

class GetNoteUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(id: Int) : Note? {
        return noteRepository.getById(id)
    }
}