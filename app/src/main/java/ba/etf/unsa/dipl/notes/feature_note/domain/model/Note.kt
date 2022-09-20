package ba.etf.unsa.dipl.notes.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ba.etf.unsa.dipl.notes.feature_note.presentation.theme.*

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val colors = listOf(
            HoneyYellow,
            Carmine,
            MintGreen,
            BabyBlue,
            Mauvelous
        )
    }
}

class InvalidNoteException(message: String): Exception(message)
