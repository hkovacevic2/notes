package ba.etf.unsa.dipl.notes.feature_note.presentation.util

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home")
    object ManipulateNoteScreen: Screen("addedit")
}