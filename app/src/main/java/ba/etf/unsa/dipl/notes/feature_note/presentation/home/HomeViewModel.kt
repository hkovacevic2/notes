package ba.etf.unsa.dipl.notes.feature_note.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ba.etf.unsa.dipl.notes.feature_note.domain.model.Note
import ba.etf.unsa.dipl.notes.feature_note.domain.usecase.NoteUseCases
import ba.etf.unsa.dipl.notes.feature_note.domain.util.NoteOrder
import ba.etf.unsa.dipl.notes.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _uiState = mutableStateOf(NotesState())
    val uiState: State<NotesState> = _uiState
    private var fetchJob: Job? = null

    private var lastDeletedNote: Note? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when(event) {
            is NotesEvent.Order -> {
                if (uiState.value.noteOrder::class == event.noteOrder::class && uiState.value.noteOrder.orderType == event.noteOrder.orderType) {
                    return
                }
                getNotes(event.noteOrder)
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNoteUseCase(event.note)
                    lastDeletedNote = event.note
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNoteUseCase(lastDeletedNote ?: return@launch)
                    lastDeletedNote = null
                }
            }
            is NotesEvent.ToggleToolbar -> {
                _uiState.value = uiState.value.copy(
                    isToolbarVisible = !uiState.value.isToolbarVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        fetchJob?.cancel()
        fetchJob = noteUseCases.getNotesUseCase(noteOrder)
            .onEach { notes ->
                _uiState.value = uiState.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }
}