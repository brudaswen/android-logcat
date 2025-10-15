package de.brudaswen.android.logcat.ui.list.usecase

import de.brudaswen.android.logcat.database.LogcatFilter
import de.brudaswen.android.logcat.database.LogcatSearchDao

internal class LogcatSuggestionUseCase(
    private val searchDao: LogcatSearchDao,
) {

    suspend operator fun invoke(query: String): List<Suggestion> {
        if (query.isEmpty()) {
            return emptyList()
        }

        val type = FilterType.entries.firstOrNull { query.startsWith(it.prefix) }

        if (type == null) {
            return simpleSearchSuggestion(query) + autoCompleteSuggestions(query)
        }

        val primarySuggestion = Suggestion.LogcatFilterSuggestion(
            type = type,
            keyword = query.removePrefix(type.prefix),
        )

        val typeSuggestions = when (type) {
            FilterType.IncludeTagContains,
            FilterType.IncludeTagExactly,
            FilterType.ExcludeTagContains,
            FilterType.ExcludeTagExactly,
            -> tagFilterSuggestions(
                type = type,
                keyword = query.removePrefix(type.prefix),
            )

            else -> emptyList()
        }

        return listOf(primarySuggestion) +
            simpleSearchSuggestion(query) +
            typeSuggestions
    }

    internal fun parseLogcatFilter(query: String): LogcatFilter =
        FilterType.entries
            .firstOrNull { query.startsWith(it.prefix) }
            ?.let { it.toLogcatFilter(query.removePrefix(it.prefix)) }
            ?: LogcatFilter.Search(keyword = query)

    private fun simpleSearchSuggestion(keyword: String) = listOf(
        Suggestion.LogcatFilterSuggestion(
            type = FilterType.Search,
            keyword = keyword,
        ),
    )

    private fun autoCompleteSuggestions(query: String) =
        FilterType.entries.filter {
            it.prefix.contains(query)
        }.map(Suggestion::AutoCompleteSuggestion)

    private suspend fun tagFilterSuggestions(
        type: FilterType,
        keyword: String,
    ): List<Suggestion> = searchDao.searchTags(query = keyword).map { tag ->
        Suggestion.LogcatFilterSuggestion(
            type = type,
            keyword = tag,
        )
    }
}
