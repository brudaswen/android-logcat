package de.brudaswen.android.logcat.ui.list.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.BugReport
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.brudaswen.android.logcat.core.data.LogcatLevel
import de.brudaswen.android.logcat.database.LogcatItemDto
import de.brudaswen.android.logcat.ui.R
import de.brudaswen.android.logcat.ui.theme.LogcatPreviewTheme
import de.brudaswen.android.logcat.ui.theme.LogcatTheme
import de.brudaswen.android.logcat.ui.theme.extension.debugColors
import de.brudaswen.android.logcat.ui.theme.extension.errorColors
import de.brudaswen.android.logcat.ui.theme.extension.fatalColors
import de.brudaswen.android.logcat.ui.theme.extension.infoColors
import de.brudaswen.android.logcat.ui.theme.extension.verboseColors
import de.brudaswen.android.logcat.ui.theme.extension.warningColors
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.char
import kotlinx.datetime.toInstant

@Composable
internal fun LogcatListItem(
    item: LogcatItemDto,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier.clickable(
            onClick = onClick,
        ),
    ) {
        val colors = when (item.level) {
            LogcatLevel.Fatal -> ListItemDefaults.fatalColors()
            LogcatLevel.Error -> ListItemDefaults.errorColors()
            LogcatLevel.Warning -> ListItemDefaults.warningColors()
            LogcatLevel.Info -> ListItemDefaults.infoColors()
            LogcatLevel.Debug -> ListItemDefaults.debugColors()
            LogcatLevel.Verbose -> ListItemDefaults.verboseColors()
            null -> ListItemDefaults.colors()
        }

        ListItem(
            leadingContent = {
                item.icon()?.let { icon ->
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                    )
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
                Column(
                    horizontalAlignment = Alignment.End,
                ) {
                    Text(
                        text = item.date.format(timeFormat),
                        style = LogcatTheme.typography.labelSmall,
                    )
                    Text(
                        text = item.date.format(dateFormat),
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
internal fun LogcatItemDto.icon(): ImageVector? = when (level) {
    LogcatLevel.Verbose -> Icons.AutoMirrored.Outlined.HelpOutline
    LogcatLevel.Debug -> Icons.Outlined.BugReport
    LogcatLevel.Info -> Icons.Outlined.Info
    LogcatLevel.Warning -> Icons.Outlined.WarningAmber
    LogcatLevel.Error -> Icons.Outlined.ErrorOutline
    LogcatLevel.Fatal -> Icons.Outlined.Error
    null -> null
}

internal val dateTimeFormat = DateTimeComponents.Format {
    day()
    char('.')
    monthNumber()
    char('.')
    year()
    chars(", ")
    hour()
    char(':')
    minute()
    char(':')
    second()
    char('.')
    secondFraction(fixedLength = 3)
}

internal val dateFormat = DateTimeComponents.Format {
    day()
    char('.')
    monthNumber()
    char('.')
    year()
}

internal val timeFormat = DateTimeComponents.Format {
    hour()
    char(':')
    minute()
    char(':')
    second()
    char('.')
    secondFraction(fixedLength = 3)
}

@Preview
@Composable
internal fun LogcatListItemPreview() = LogcatPreviewTheme {
    LogcatListItem(
        item = LogcatItemDto(
            uuid = "uuid",
            date = LocalDateTime(2025, 10, 6, 13, 26, 5, 42_123_456).toInstant(TimeZone.UTC),
            pid = 42876,
            tid = 97371,
            level = LogcatLevel.Info,
            tag = "DisplayManager",
            message = "Choreographer implicitly registered for the refresh rate.",
        ),
        onClick = {},
    )
}
