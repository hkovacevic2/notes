package ba.etf.unsa.dipl.notes.feature_note.presentation.addedit

import androidx.compose.ui.focus.FocusState

sealed class ManipulateNoteEvent {
    data class NewTitle(val value: String): ManipulateNoteEvent()
    data class NewContent(val value: String): ManipulateNoteEvent()
    data class FocusTitle(val focused: FocusState): ManipulateNoteEvent()
    data class FocusContent(val focused: FocusState): ManipulateNoteEvent()
    data class NewColor(val color: Int) : ManipulateNoteEvent()
    object SaveNote: ManipulateNoteEvent()
}