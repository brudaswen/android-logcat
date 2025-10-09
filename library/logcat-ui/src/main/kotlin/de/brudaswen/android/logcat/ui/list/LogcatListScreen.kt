package de.brudaswen.android.logcat.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import de.brudaswen.android.logcat.database.LogcatItemDto
import de.brudaswen.android.logcat.ui.list.ui.LogcatList

@Composable
public fun LogcatListScreen(
    onItemClick: (LogcatItemDto) -> Unit,
) {
    val context = LocalContext.current
    val viewModel = viewModel<LogcatListViewModel> { LogcatListViewModel(context) }
    val logcatItems = viewModel.logcatItems.collectAsLazyPagingItems()

    LogcatList(
        logcatItems = logcatItems,
        onExportAsTxtClick = viewModel::onExportAsTxtClick,
        onExportAsCsvClick = viewModel::onExportAsCsvClick,
        onItemClick = onItemClick,
    )
}
