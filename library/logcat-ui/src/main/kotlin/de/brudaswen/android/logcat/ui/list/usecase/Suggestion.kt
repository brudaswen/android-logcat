package de.brudaswen.android.logcat.ui.list.usecase

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import de.brudaswen.android.logcat.database.LogcatFilter

internal sealed interface Suggestion {
    data class AutoCompleteSuggestion(
        val type: FilterType,
    ) : Suggestion {
        val query: String
            get() = type.prefix
    }

    data class LogcatFilterSuggestion(
        val type: FilterType,
        val keyword: String,
    ) : Suggestion {
        val filter: LogcatFilter
            get() = type.toLogcatFilter(keyword)
    }
}

internal val Suggestion.icon: ImageVector
    get() = when (this) {
        is Suggestion.AutoCompleteSuggestion -> type.icon
        is Suggestion.LogcatFilterSuggestion -> type.icon
    }

@Composable
internal fun Suggestion.title(): String = when (this) {
    is Suggestion.AutoCompleteSuggestion -> "${type.prefix} …"
    is Suggestion.LogcatFilterSuggestion -> type.query(keyword)
}

@Composable
internal fun Suggestion.description(): String = when (this) {
    is Suggestion.AutoCompleteSuggestion -> type.description()
    is Suggestion.LogcatFilterSuggestion -> type.description(keyword)
}
