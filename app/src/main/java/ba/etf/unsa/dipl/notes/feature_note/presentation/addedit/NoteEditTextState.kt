package ba.etf.unsa.dipl.notes.feature_note.presentation.addedit

data class NoteEditTextState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)
