package de.brudaswen.android.logcat.ui.component.scaffold.search

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.SearchBar
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.brudaswen.android.logcat.core.data.LogcatLevel
import de.brudaswen.android.logcat.database.LogcatFilter
import de.brudaswen.android.logcat.ui.R
import de.brudaswen.android.logcat.ui.list.usecase.Suggestion

@Composable
internal fun LogcatSearchBar(
    textFieldState: TextFieldState,
    itemCount: Int,
    suggestions: List<Suggestion>,
    filters: List<LogcatFilter>,
    onSearch: (String) -> Unit,
    onAddFilterClick: (LogcatFilter) -> Unit,
    onRemoveFilterClick: (LogcatFilter) -> Unit,
    onLevelSelectionChanged: (LogcatLevel, Boolean) -> Unit,
) {
    val searchBarState = rememberSearchBarState()

    val filterCount = filters.size

    val placeholder = stringResource(
        R.string.logcat_list_searchbar_placeholder,
        pluralStringResource(
            R.plurals.logcat_list_item_count,
            itemCount,
            itemCount,
        ),
        pluralStringResource(
            R.plurals.logcat_list_filter_count,
            filterCount,
            filterCount,
        ),
    )

    SearchBar(
        modifier = Modifier.padding(bottom = 8.dp),
        state = searchBarState,
        inputField = {
            LogcatSearchInputField(
                textFieldState = textFieldState,
                searchBarState = searchBarState,
                placeholder = placeholder,
                enabled = false,
                onSearch = {},
                onCloseClick = null,
            )
        },
    )

    LogcatExpandedSearchBar(
        textFieldState = textFieldState,
        searchBarState = searchBarState,
        placeholder = placeholder,
        suggestions = suggestions,
        filters = filters,
        onSearch = onSearch,
        onAddFilterClick = onAddFilterClick,
        onRemoveFilterClick = onRemoveFilterClick,
        onLevelSelectionChanged = onLevelSelectionChanged,
    )
}
