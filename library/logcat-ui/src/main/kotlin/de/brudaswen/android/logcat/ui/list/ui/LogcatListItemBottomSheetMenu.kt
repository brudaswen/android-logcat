package de.brudaswen.android.logcat.ui.list.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import de.brudaswen.android.logcat.database.LogcatFilter
import de.brudaswen.android.logcat.database.LogcatItemDto
import de.brudaswen.android.logcat.ui.R
import de.brudaswen.android.logcat.ui.list.usecase.FilterType.ExcludeMessageExactly
import de.brudaswen.android.logcat.ui.list.usecase.FilterType.ExcludeTagExactly
import de.brudaswen.android.logcat.ui.list.usecase.FilterType.IncludeMessageExactly
import de.brudaswen.android.logcat.ui.list.usecase.FilterType.IncludeTagExactly
import de.brudaswen.android.logcat.ui.list.usecase.icon
import de.brudaswen.android.logcat.ui.list.usecase.query
import de.brudaswen.android.logcat.ui.list.usecase.toLogcatFilter
import de.brudaswen.android.logcat.ui.preview.LogcatItemDtoPreviewProvider
import de.brudaswen.android.logcat.ui.theme.LogcatPreviewTheme
import de.brudaswen.android.logcat.ui.theme.LogcatTheme

@Composable
internal fun LogcatListItemBottomSheetMenu(
    item: LogcatItemDto,
    onAddFilterClick: (LogcatFilter) -> Unit,
    onDismissRequest: () -> Unit,
) {
    ModalBottomSheet(
        containerColor = LogcatTheme.colorScheme.surface,
        onDismissRequest = onDismissRequest,
    ) {
        LogcatListItemBottomSheetContent(
            item = item,
            onAddFilterClick = onAddFilterClick,
            onDismissRequest = onDismissRequest,
        )
    }
}

@Composable
private fun LogcatListItemBottomSheetContent(
    item: LogcatItemDto,
    onAddFilterClick: (LogcatFilter) -> Unit,
    onDismissRequest: () -> Unit,
) {
    Column {
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 4.dp),
            text = item.tag,
            style = LogcatTheme.typography.headlineSmall,
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            text = item.message,
            style = LogcatTheme.typography.bodySmall,
            maxLines = 2,
        )

        listOf(
            IncludeTagExactly to stringResource(R.string.logcat_list_item_menu_include_tag_title),
            ExcludeTagExactly to stringResource(R.string.logcat_list_item_menu_exclude_tag_title),
        ).forEach { (type, title) ->
            MenuItem(
                icon = type.icon,
                title = title,
                description = type.query(keyword = item.tag),
                onClick = { onAddFilterClick(type.toLogcatFilter(item.tag)) },
                onDismissRequest = onDismissRequest,
            )
        }

        listOf(
            IncludeMessageExactly to stringResource(R.string.logcat_list_item_menu_include_message_title),
            ExcludeMessageExactly to stringResource(R.string.logcat_list_item_menu_exclude_message_title),
        ).forEach { (type, title) ->
            MenuItem(
                icon = type.icon,
                title = title,
                description = type.query(keyword = item.message),
                onClick = { onAddFilterClick(type.toLogcatFilter(item.message)) },
                onDismissRequest = onDismissRequest,
            )
        }
    }
}

@Composable
private fun MenuItem(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    ListItem(
        modifier = Modifier.clickable(
            onClick = {
                onClick()
                onDismissRequest()
            },
        ),
        leadingContent = {
            Icon(icon, null)
        },
        headlineContent = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        supportingContent = {
            Text(
                text = description,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
    )
}

@Preview
@Composable
internal fun LogcatListItemBottomSheetContentPreview(
    @PreviewParameter(LogcatItemDtoPreviewProvider::class)
    item: LogcatItemDto,
) = LogcatPreviewTheme {
    LogcatListItemBottomSheetContent(
        item = item,
        onAddFilterClick = {},
        onDismissRequest = {},
    )
}
