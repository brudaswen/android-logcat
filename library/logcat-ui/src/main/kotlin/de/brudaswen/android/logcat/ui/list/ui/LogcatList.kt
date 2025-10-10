package de.brudaswen.android.logcat.ui.list.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.TextSnippet
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.TableChart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.LazyPagingItems
import de.brudaswen.android.logcat.database.LogcatItemDto
import de.brudaswen.android.logcat.ui.R
import de.brudaswen.android.logcat.ui.common.LogcatScaffold
import de.brudaswen.android.logcat.ui.theme.LogcatTheme

@Composable
internal fun LogcatList(
    logcatItems: LazyPagingItems<LogcatItemDto>,
    onExportAsTxtClick: () -> Unit,
    onExportAsCsvClick: () -> Unit,
    onItemClick: (LogcatItemDto) -> Unit,
) {
    var showMenu by remember { mutableStateOf(false) }

    LogcatScaffold(
        title = stringResource(R.string.logcat_list_title),
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
    ) {
        LazyColumn(
            reverseLayout = true,
        ) {
            item {
                Spacer(
                    modifier = Modifier.windowInsetsBottomHeight(WindowInsets.systemBars),
                )
            }

            items(
                items = logcatItems,
            ) { item ->
                LogcatListItem(
                    item = item,
                    onClick = { onItemClick(item) },
                )
            }
        }
    }

    if (showMenu) {
        ModalBottomSheet(
            containerColor = LogcatTheme.colorScheme.surface,
            onDismissRequest = { showMenu = false },
        ) {
            MenuItem(
                text = stringResource(R.string.logcat_list_menu_share_txt_title),
                icon = Icons.AutoMirrored.Outlined.TextSnippet,
                onClick = {
                    onExportAsTxtClick()
                    showMenu = false
                },
            )
            MenuItem(
                text = stringResource(R.string.logcat_list_menu_share_csv_title),
                icon = Icons.Outlined.TableChart,
                onClick = {
                    onExportAsCsvClick()
                    showMenu = false
                },
            )
        }
    }
}

@Composable
private fun MenuItem(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    ListItem(
        modifier = Modifier.clickable(
            onClick = onClick,
        ),
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null,
            )
        },
        headlineContent = {
            Text(
                text = text,
                style = LogcatTheme.typography.bodyMedium,
            )
        },
    )
}

internal fun <T : Any> LazyListScope.items(
    items: LazyPagingItems<T>,
    key: ((item: T) -> Any)? = null,
    contentType: (item: T) -> Any? = { null },
    itemContent: @Composable LazyItemScope.(item: T) -> Unit,
) = items(
    count = items.itemCount,
    key = if (key != null) { index: Int -> key(items[index]!!) } else null,
    contentType = { index: Int -> contentType(items[index]!!) },
    itemContent = { index: Int -> itemContent(items[index]!!) },
)
