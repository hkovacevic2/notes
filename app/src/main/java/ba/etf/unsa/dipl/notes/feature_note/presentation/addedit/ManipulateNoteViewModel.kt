package ba.etf.unsa.dipl.notes.feature_note.presentation.addedit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ba.etf.unsa.dipl.notes.R
import ba.etf.unsa.dipl.notes.feature_note.domain.model.InvalidNoteException
import ba.etf.unsa.dipl.notes.feature_note.domain.model.Note
import ba.etf.unsa.dipl.notes.feature_note.domain.usecase.NoteUseCases
import ba.etf.unsa.dipl.notes.feature_note.domain.util.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManipulateNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    resourcesProvider: ResourcesProvider,
    savesStateHandle: SavedStateHandle
): ViewModel() {

    private val _noteTitle = mutableStateOf(NoteEditTextState(hint = resourcesProvider.getString(R.string.title_hint)))
    val noteTitle : State<NoteEditTextState> = _noteTitle

    private val _noteContent = mutableStateOf(NoteEditTextState(hint = resourcesProvider.getString(R.string.content_hint)))
    val noteContent : State<NoteEditTextState> = _noteContent

    private val _noteColor = mutableStateOf(Note.colors.random().toArgb())
    val noteColor : State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var selectedId: Int? = null

    init {
        savesStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNoteUseCase(noteId)?.also {
                        selectedId = it.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = it.title,
                            isHintVisible = false
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = it.content,
                            isHintVisible = false
                        )
                        _noteColor.value = it.color
                    }
                }
            }
        }
    }

    fun onEvent(event: ManipulateNoteEvent) {
        when(event) {
            is ManipulateNoteEvent.NewTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }
            is ManipulateNoteEvent.NewContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.value
                )
            }
            is ManipulateNoteEvent.NewColor -> {
                _noteColor.value = event.color
            }
            is ManipulateNoteEvent.FocusTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focused.isFocused && noteTitle.value.text.isBlank()
                )
            }
            is ManipulateNoteEvent.FocusContent -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focused.isFocused && noteContent.value.text.isBlank()
                )
            }
            ManipulateNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNoteUseCase(Note(
                            title = noteTitle.value.text,
                            content = noteContent.value.text,
                            timestamp = System.currentTimeMillis(),
                            color = noteColor.value,
                            id = selectedId
                        ))
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (exception: InvalidNoteException) {
                        _eventFlow.emit(UiEvent.ShowSnackbar(exception.message ?: "Neočekivana greška :("))
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveNote: UiEvent()
    }
}