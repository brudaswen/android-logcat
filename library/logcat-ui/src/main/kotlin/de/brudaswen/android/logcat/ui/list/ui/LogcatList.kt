package de.brudaswen.android.logcat.ui.list.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.paging.compose.LazyPagingItems
import de.brudaswen.android.logcat.database.LogcatItemDto
import de.brudaswen.android.logcat.ui.common.LogcatScaffold
import de.brudaswen.android.logcat.ui.extension.items

@Composable
internal fun LogcatList(
    logcatItems: LazyPagingItems<LogcatItemDto>,
    onExportAsTxtClick: () -> Unit,
    onExportAsCsvClick: () -> Unit,
    onItemClick: (LogcatItemDto) -> Unit,
) {
    var showMenu by remember { mutableStateOf(false) }

    LogcatScaffold(
        actions = {
            IconButton(
                onClick = { showMenu = true },
            ) {
                Icon(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = null,
                )
            }
        },
        onUpClick = null,
    ) { contentPadding ->
        LazyColumn(
            contentPadding = contentPadding,
            reverseLayout = true,
        ) {
            items(
                items = logcatItems,
                key = { it.uuid },
            ) { item ->
                LogcatListItem(
                    item = item,
                    onClick = { onItemClick(item) },
                )
            }
        }
    }

    if (showMenu) {
        LogcatListMenu(
            onExportAsTxtClick = onExportAsTxtClick,
            onExportAsCsvClick = onExportAsCsvClick,
            onDismissRequest = { showMenu = false },
        )
    }
}
