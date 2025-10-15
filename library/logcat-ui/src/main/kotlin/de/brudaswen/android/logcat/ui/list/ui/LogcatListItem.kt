package de.brudaswen.android.logcat.ui.list.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.BugReport
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import de.brudaswen.android.logcat.core.data.LogcatLevel
import de.brudaswen.android.logcat.database.LogcatItemDto
import de.brudaswen.android.logcat.ui.R
import de.brudaswen.android.logcat.ui.extension.datetime.dateFormat
import de.brudaswen.android.logcat.ui.extension.datetime.timeFormat
import de.brudaswen.android.logcat.ui.preview.LogcatItemDtoPreviewProvider
import de.brudaswen.android.logcat.ui.theme.LogcatPreviewTheme
import de.brudaswen.android.logcat.ui.theme.LogcatTheme
import de.brudaswen.android.logcat.ui.theme.extension.cardColors
import de.brudaswen.android.logcat.ui.theme.extension.listItemColors
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime

@Composable
internal fun LogcatListItem(
    item: LogcatItemDto,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)?,
) {
    Column(
        modifier = Modifier.combinedClickable(
            onClick = onClick,
            onLongClick = onLongClick,
        ),
    ) {
        val colors = item.level?.listItemColors() ?: ListItemDefaults.colors()
        val cardColors = item.level?.cardColors() ?: CardDefaults.cardColors()

        ListItem(
            leadingContent = {
                item.icon()?.let { icon ->
                    Surface(
                        color = cardColors.containerColor,
                        contentColor = cardColors.contentColor,
                        shape = CircleShape,
                    ) {
                        Icon(
                            modifier = Modifier.padding(12.dp),
                            imageVector = icon,
                            contentDescription = null,
                        )
                    }
                }
            },
            overlineContent = {
                Text(
                    text = item.level.toString(),
                    style = LogcatTheme.typography.bodySmall,
                )
            },
            headlineContent = {
                Text(
                    text = item.tag,
                    style = LogcatTheme.typography.titleMedium,
                )
            },
            trailingContent = {
                val dateTime = item.date.toLocalDateTime(TimeZone.currentSystemDefault())

                Column(
                    horizontalAlignment = Alignment.End,
                ) {
                    Text(
                        text = dateTime.format(timeFormat),
                        style = LogcatTheme.typography.labelSmall,
                    )
                    Text(
                        text = dateTime.format(dateFormat),
                        style = LogcatTheme.typography.labelSmall,
                    )
                }
            },
            supportingContent = {
                Column {
                    Text(
                        text = item.message,
                        maxLines = 2,
                        overflow = Ellipsis,
                        style = LogcatTheme.typography.bodyMedium,
                    )
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = stringResource(
                            R.string.logcat_list_item_tid_pid,
                            item.tid,
                            item.pid,
                        ),
                        style = LogcatTheme.typography.labelMedium,
                    )
                }
            },
            colors = colors,
        )

        HorizontalDivider(
            modifier = Modifier
                .background(colors.containerColor)
                .padding(horizontal = 16.dp),
        )
    }
}

@Composable
internal fun LogcatItemDto.icon(): ImageVector? = level?.icon()

internal fun LogcatLevel.icon(): ImageVector = when (this) {
    LogcatLevel.Verbose -> Icons.AutoMirrored.Outlined.HelpOutline
    LogcatLevel.Debug -> Icons.Outlined.BugReport
    LogcatLevel.Info -> Icons.Outlined.Info
    LogcatLevel.Warning -> Icons.Outlined.WarningAmber
    LogcatLevel.Error -> Icons.Outlined.ErrorOutline
    LogcatLevel.Fatal -> Icons.Outlined.Error
}

@Preview
@Composable
internal fun LogcatListItemPreview(
    @PreviewParameter(LogcatItemDtoPreviewProvider::class)
    item: LogcatItemDto,
) = LogcatPreviewTheme {
    LogcatListItem(
        item = item,
        onClick = {},
        onLongClick = {},
    )
}
