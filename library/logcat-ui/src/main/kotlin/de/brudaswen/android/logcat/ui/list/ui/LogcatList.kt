package de.brudaswen.android.logcat.ui.list.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import de.brudaswen.android.logcat.core.data.LogcatLevel
import de.brudaswen.android.logcat.database.LogcatFilter
import de.brudaswen.android.logcat.database.LogcatItemDto
import de.brudaswen.android.logcat.ui.component.scaffold.search.LogcatSearchScaffold
import de.brudaswen.android.logcat.ui.extension.items
import de.brudaswen.android.logcat.ui.list.usecase.Suggestion

@Composable
internal fun LogcatList(
    textFieldState: TextFieldState,
    logcatItems: LazyPagingItems<LogcatItemDto>,
    suggestions: List<Suggestion>,
    filters: List<LogcatFilter>,
    onSearch: (String) -> Unit,
    onLevelSelectionChanged: (LogcatLevel, Boolean) -> Unit,
    onAddFilterClick: (LogcatFilter) -> Unit,
    onRemoveFilterClick: (LogcatFilter) -> Unit,
    onExportAsTxtClick: () -> Unit,
    onExportAsCsvClick: () -> Unit,
    onResetDatabaseClick: () -> Unit,
    onResetFiltersClick: () -> Unit,
    onItemClick: (LogcatItemDto) -> Unit,
    onItemLongClick: (LogcatItemDto) -> Unit,
) {
    var showMenu by remember { mutableStateOf(false) }

    LogcatSearchScaffold(
        textFieldState = textFieldState,
        itemCount = logcatItems.itemCount,
        suggestions = suggestions,
        filters = filters,
        onSearch = onSearch,
        onLevelSelectionChanged = onLevelSelectionChanged,
        onAddFilterClick = onAddFilterClick,
        onRemoveFilterClick = onRemoveFilterClick,
        actions = {
            LogcatListMenu(
                onOpenMenuClick = { showMenu = true },
            )
        },
        onUpClick = null,
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding,
            verticalArrangement = when (logcatItems.itemCount) {
                0 -> Arrangement.Center
                else -> Arrangement.Top
            },
            reverseLayout = true,
        ) {
            if (logcatItems.itemCount <= 0) {
                item {
                    LogcatListEmpty(
                        onResetFiltersClick = onResetFiltersClick,
                    )
                }
            } else {
                items(
                    items = logcatItems,
                    key = { it.uuid },
                ) { item ->
                    LogcatListItem(
                        item = item,
                        onClick = { onItemClick(item) },
                        onLongClick = { onItemLongClick(item) },
                    )
                }
            }
        }
    }

    if (showMenu) {
        LogcatListBottomSheet(
            onExportAsTxtClick = onExportAsTxtClick,
            onExportAsCsvClick = onExportAsCsvClick,
            onResetDatabaseClick = onResetDatabaseClick,
            onDismissRequest = { showMenu = false },
        )
    }
}
