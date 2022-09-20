package ba.etf.unsa.dipl.notes.feature_note.presentation.home

import ba.etf.unsa.dipl.notes.feature_note.domain.model.Note
import ba.etf.unsa.dipl.notes.feature_note.domain.util.NoteOrder
import ba.etf.unsa.dipl.notes.feature_note.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isToolbarVisible: Boolean = false
)
