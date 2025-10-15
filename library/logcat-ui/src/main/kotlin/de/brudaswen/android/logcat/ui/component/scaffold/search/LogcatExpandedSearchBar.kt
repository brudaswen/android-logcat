package de.brudaswen.android.logcat.ui.component.scaffold.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExpandedDockedSearchBar
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBarState
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.brudaswen.android.logcat.core.data.LogcatLevel
import de.brudaswen.android.logcat.database.LogcatFilter
import de.brudaswen.android.logcat.database.LogcatFilter.QueryPrecision.Contains
import de.brudaswen.android.logcat.database.LogcatFilter.QueryPrecision.Exactly
import de.brudaswen.android.logcat.ui.R
import de.brudaswen.android.logcat.ui.list.ui.icon
import de.brudaswen.android.logcat.ui.list.usecase.FilterType
import de.brudaswen.android.logcat.ui.list.usecase.Suggestion
import de.brudaswen.android.logcat.ui.list.usecase.description
import de.brudaswen.android.logcat.ui.list.usecase.icon
import de.brudaswen.android.logcat.ui.list.usecase.title
import de.brudaswen.android.logcat.ui.theme.LogcatPreviewTheme
import de.brudaswen.android.logcat.ui.theme.LogcatTheme
import de.brudaswen.android.logcat.ui.theme.extension.filterChipBorder
import de.brudaswen.android.logcat.ui.theme.extension.filterChipColors
import kotlinx.coroutines.launch

@Composable
internal fun LogcatExpandedSearchBar(
    textFieldState: TextFieldState,
    searchBarState: SearchBarState,
    placeholder: String,
    suggestions: List<Suggestion>,
    filters: List<LogcatFilter>,
    onSearch: (String) -> Unit,
    onAddFilterClick: (LogcatFilter) -> Unit,
    onRemoveFilterClick: (LogcatFilter) -> Unit,
    onLevelSelectionChanged: (LogcatLevel, Boolean) -> Unit,
) {
    val scope = rememberCoroutineScope()

    val inputField = @Composable {
        LogcatSearchInputField(
            textFieldState = textFieldState,
            searchBarState = searchBarState,
            placeholder = placeholder,
            onSearch = {
                onSearch(it)
                textFieldState.setTextAndPlaceCursorAtEnd("")
            },
            onCloseClick = {
                textFieldState.setTextAndPlaceCursorAtEnd("")
                scope.launch {
                    searchBarState.animateToCollapsed()
                }
            },
        )
    }

    ExpandedDockedSearchBar(
        state = searchBarState,
        inputField = inputField,
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
        ) {
            if (textFieldState.text.isNotEmpty()) {
                SearchSuggestions(
                    suggestions = suggestions,
                    onSuggestionClick = { suggestion ->
                        when (suggestion) {
                            is Suggestion.AutoCompleteSuggestion ->
                                textFieldState.setTextAndPlaceCursorAtEnd(suggestion.query)

                            is Suggestion.LogcatFilterSuggestion -> {
                                onAddFilterClick(suggestion.filter)
                                textFieldState.setTextAndPlaceCursorAtEnd("")
                            }
                        }
                    },
                )
            } else {
                SearchFilters(
                    filters = filters,
                    onRemoveFilterClick = onRemoveFilterClick,
                    onLevelSelectionChanged = onLevelSelectionChanged,
                )
            }
        }
    }
}

@Composable
private fun SearchSuggestions(
    suggestions: List<Suggestion>,
    onSuggestionClick: (Suggestion) -> Unit,
) {
    suggestions.forEach { suggestion ->
        ListItem(
            modifier = Modifier.clickable(
                onClick = { onSuggestionClick(suggestion) },
            ),
            leadingContent = {
                Icon(suggestion.icon, null)
            },
            headlineContent = {
                Text(
                    text = suggestion.title(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            supportingContent = {
                Text(
                    text = suggestion.description(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            },
        )
    }
}

@Composable
private fun SearchFilters(
    filters: List<LogcatFilter>,
    onRemoveFilterClick: (LogcatFilter) -> Unit,
    onLevelSelectionChanged: (LogcatLevel, Boolean) -> Unit,
) {
    val searchFilters = remember(filters) { filters.filterIsInstance<LogcatFilter.Search>() }
    val tagFilters = remember(filters) { filters.filterIsInstance<LogcatFilter.Tag>() }
    val messageFilters = remember(filters) { filters.filterIsInstance<LogcatFilter.Message>() }
    val levelFilters = remember(filters) { filters.filterIsInstance<LogcatFilter.Level>() }

    Text(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp),
        text = stringResource(R.string.logcat_list_filter_explanation),
        style = LogcatTheme.typography.labelSmall,
    )

    if (searchFilters.isNotEmpty()) {
        SearchInputChips(
            textQueries = searchFilters,
            onRemoveFilterClick = onRemoveFilterClick,
        )
    }

    if (tagFilters.isNotEmpty()) {
        TagInputChips(
            tags = tagFilters,
            onRemoveFilterClick = onRemoveFilterClick,
        )
    }

    if (messageFilters.isNotEmpty()) {
        MessageInputChips(
            messages = messageFilters,
            onRemoveFilterClick = onRemoveFilterClick,
        )
    }

    LevelFilterChips(
        levels = levelFilters,
        onLevelSelectionChanged = onLevelSelectionChanged,
    )
}

@Composable
private fun SearchInputChips(
    textQueries: List<LogcatFilter.Search>,
    onRemoveFilterClick: (LogcatFilter) -> Unit,
) {
    Headline(
        title = stringResource(R.string.logcat_list_search_filters_title),
    )

    FlowRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = spacedBy(12.dp),
    ) {
        textQueries.forEach { filter ->
            LogcatFilterInputChip(
                filter = filter,
                onRemoveFilterClick = onRemoveFilterClick,
            )
        }
    }
}

@Composable
private fun TagInputChips(
    tags: List<LogcatFilter.Tag>,
    onRemoveFilterClick: (LogcatFilter) -> Unit,
) {
    Headline(
        title = stringResource(R.string.logcat_list_tag_filters_title),
    )

    FlowRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = spacedBy(12.dp),
    ) {
        tags.forEach { filter ->
            LogcatFilterInputChip(
                filter = filter,
                onRemoveFilterClick = onRemoveFilterClick,
            )
        }
    }
}

@Composable
private fun MessageInputChips(
    messages: List<LogcatFilter.Message>,
    onRemoveFilterClick: (LogcatFilter) -> Unit,
) {
    Headline(
        title = stringResource(R.string.logcat_list_message_filters_title),
    )

    FlowRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = spacedBy(12.dp),
    ) {
        messages.forEach { filter ->
            LogcatFilterInputChip(
                filter = filter,
                onRemoveFilterClick = onRemoveFilterClick,
            )
        }
    }
}

@Composable
private fun LevelFilterChips(
    levels: List<LogcatFilter.Level>,
    onLevelSelectionChanged: (LogcatLevel, Boolean) -> Unit,
) {
    Headline(
        title = stringResource(R.string.logcat_list_level_filters_title),
    )

    FlowRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = spacedBy(12.dp),
    ) {
        LogcatLevel.entries.forEach { level ->
            val selected = levels.any { it.level == level }

            FilterChip(
                selected = selected,
                label = { Text(level.name) },
                leadingIcon = { Icon(level.icon(), null) },
                colors = level.filterChipColors(),
                border = level.filterChipBorder(selected),
                onClick = { onLevelSelectionChanged(level, !selected) },
            )
        }
    }
}

@Composable
private fun Headline(title: String) {
    Text(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        text = title,
        style = LogcatTheme.typography.labelMedium,
    )
}

@Composable
private fun LogcatFilterInputChip(
    filter: LogcatFilter,
    onRemoveFilterClick: (LogcatFilter) -> Unit,
) {
    InputChip(
        selected = true,
        label = {
            Text(
                text = filter.label(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        leadingIcon = { Icon(filter.icon(), null) },
        trailingIcon = { Icon(Icons.Rounded.Close, null) },
        onClick = { onRemoveFilterClick(filter) },
    )
}

private fun LogcatFilter.icon() = when (this) {
    is LogcatFilter.Search -> FilterType.Search.icon

    is LogcatFilter.Tag.Include -> when (precision) {
        Exactly -> FilterType.IncludeTagExactly.icon
        Contains -> FilterType.IncludeTagContains.icon
    }

    is LogcatFilter.Tag.Exclude -> when (precision) {
        Exactly -> FilterType.ExcludeTagExactly.icon
        Contains -> FilterType.ExcludeTagContains.icon
    }

    is LogcatFilter.Message.Include -> when (precision) {
        Exactly -> FilterType.IncludeMessageExactly.icon
        Contains -> FilterType.IncludeMessageContains.icon
    }

    is LogcatFilter.Message.Exclude -> when (precision) {
        Exactly -> FilterType.ExcludeMessageExactly.icon
        Contains -> FilterType.ExcludeMessageContains.icon
    }

    is LogcatFilter.Level -> level.icon()
}

private fun LogcatFilter.label() = when (this) {
    is LogcatFilter.Search -> keyword

    is LogcatFilter.Tag -> when (precision) {
        Exactly -> "'$keyword'"
        Contains -> keyword
    }

    is LogcatFilter.Message -> when (precision) {
        Exactly -> "'$keyword'"
        Contains -> keyword
    }

    is LogcatFilter.Level -> level.name
}

@Preview
@Composable
internal fun LogcatExpandedSearchBarPreview() = LogcatPreviewTheme {
    LogcatExpandedSearchBar(
        textFieldState = TextFieldState(),
        searchBarState = rememberSearchBarState(SearchBarValue.Expanded),
        placeholder = "Placeholder",
        suggestions = emptyList(),
        filters = listOf(
            LogcatFilter.Search("keyword"),
            LogcatFilter.Tag.Include("tag1", Contains),
            LogcatFilter.Tag.Include("tag2", Exactly),
            LogcatFilter.Tag.Exclude("tag3", Contains),
            LogcatFilter.Tag.Exclude("tag4", Exactly),
            LogcatFilter.Message.Include("message1", Contains),
            LogcatFilter.Message.Include("message2", Exactly),
            LogcatFilter.Message.Exclude("message3", Contains),
            LogcatFilter.Message.Exclude("message4", Exactly),
            LogcatFilter.Level(LogcatLevel.Warning),
            LogcatFilter.Level(LogcatLevel.Info),
            LogcatFilter.Level(LogcatLevel.Debug),
        ),
        onSearch = {},
        onAddFilterClick = {},
        onRemoveFilterClick = {},
        onLevelSelectionChanged = { _, _ -> },
    )
}

@Preview
@Composable
internal fun LogcatExpandedSearchBarSuggestionPreview() = LogcatPreviewTheme {
    LogcatExpandedSearchBar(
        textFieldState = TextFieldState("keyword"),
        searchBarState = rememberSearchBarState(SearchBarValue.Expanded),
        placeholder = "Placeholder",
        suggestions = listOf(
            Suggestion.AutoCompleteSuggestion(FilterType.Search),
            Suggestion.AutoCompleteSuggestion(FilterType.IncludeMessageContains),
            Suggestion.AutoCompleteSuggestion(FilterType.ExcludeMessageExactly),
            Suggestion.LogcatFilterSuggestion(FilterType.Search, "keyword"),
            Suggestion.LogcatFilterSuggestion(FilterType.IncludeTagContains, "keyword"),
            Suggestion.LogcatFilterSuggestion(FilterType.ExcludeTagExactly, "keyword"),
        ),
        filters = emptyList(),
        onSearch = {},
        onAddFilterClick = {},
        onRemoveFilterClick = {},
        onLevelSelectionChanged = { _, _ -> },
    )
}
