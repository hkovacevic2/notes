package ba.etf.unsa.dipl.notes.feature_note.domain.util

import android.content.Context
import androidx.annotation.StringRes

class ResourcesProvider(
    private val context: Context
) {
    fun getString(@StringRes stringResId: Int): String {
        return context.getString(stringResId)
    }
}