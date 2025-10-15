package de.brudaswen.android.logcat.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import de.brudaswen.android.logcat.database.LogcatItemDto
import de.brudaswen.android.logcat.ui.list.ui.LogcatList
import de.brudaswen.android.logcat.ui.list.ui.LogcatListItemBottomSheetMenu

@Composable
public fun LogcatListScreen(
    onItemClick: (LogcatItemDto) -> Unit,
) {
    val context = LocalContext.current
    val viewModel = viewModel<LogcatListViewModel> { LogcatListViewModel(context) }
    val logcatItems = viewModel.logcatItems.collectAsLazyPagingItems()
    val suggestions by viewModel.suggestions.collectAsStateWithLifecycle()
    val filters by viewModel.filters.collectAsStateWithLifecycle()

    var menuItem by remember { mutableStateOf<LogcatItemDto?>(null) }

    LogcatList(
        textFieldState = viewModel.textFieldState,
        logcatItems = logcatItems,
        suggestions = suggestions,
        filters = filters,
        onSearch = viewModel::onSearch,
        onLevelSelectionChanged = viewModel::onLevelSelectionChanged,
        onAddFilterClick = viewModel::onAddFilterClick,
        onRemoveFilterClick = viewModel::onRemoveFilterClick,
        onExportAsTxtClick = viewModel::onExportAsTxtClick,
        onExportAsCsvClick = viewModel::onExportAsCsvClick,
        onResetDatabaseClick = viewModel::onResetDatabaseClick,
        onResetFiltersClick = viewModel::onResetFiltersClick,
        onItemClick = onItemClick,
        onItemLongClick = { menuItem = it },
    )

    menuItem?.let { item ->
        LogcatListItemBottomSheetMenu(
            item = item,
            onAddFilterClick = viewModel::onAddFilterClick,
            onDismissRequest = { menuItem = null },
        )
    }
}
