package ba.etf.unsa.dipl.notes.feature_note.domain.usecase

import ba.etf.unsa.dipl.notes.feature_note.domain.model.Note
import ba.etf.unsa.dipl.notes.feature_note.domain.repository.NoteRepository
import ba.etf.unsa.dipl.notes.feature_note.domain.util.NoteOrder
import ba.etf.unsa.dipl.notes.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase(
    private val repository: NoteRepository
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getAll().map { it.sortedBy(noteOrder) }
    }

    private fun List<Note>.sortedBy(noteOrder: NoteOrder) =
        when(noteOrder.orderType) {
            OrderType.Descending -> {
                when(noteOrder) {
                    is NoteOrder.Title -> this.sortedBy { it.title.lowercase() }
                    is NoteOrder.Date -> this.sortedBy { it.timestamp }
                    is NoteOrder.Color -> this.sortedBy { it.color }
                }
            }
            else -> {
                when(noteOrder) {
                    is NoteOrder.Title -> this.sortedByDescending { it.title.lowercase() }
                    is NoteOrder.Date -> this.sortedByDescending { it.timestamp }
                    is NoteOrder.Color -> this.sortedByDescending { it.color }
                }
            }
        }
}