package ba.etf.unsa.dipl.notes.feature_note.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import ba.etf.unsa.dipl.notes.feature_note.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDB: RoomDatabase() {
    abstract val noteDao: NoteDAO

    companion object {
        const val NAME = "notes_db"
    }
}