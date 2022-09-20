package ba.etf.unsa.dipl.notes.feature_note.domain.usecase

import ba.etf.unsa.dipl.notes.feature_note.domain.model.InvalidNoteException
import ba.etf.unsa.dipl.notes.feature_note.domain.model.Note
import ba.etf.unsa.dipl.notes.feature_note.domain.repository.NoteRepository

class AddNoteUseCase(
    private val noteRepository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("Naslov bilješke ne može biti prazan!")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("Sadržaj bilješke ne može biti prazan!")
        }
        noteRepository.insert(note)
    }
}