package de.brudaswen.android.logcat.ui.details.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.brudaswen.android.logcat.core.data.LogcatLevel
import de.brudaswen.android.logcat.database.LogcatItemDto
import de.brudaswen.android.logcat.ui.R
import de.brudaswen.android.logcat.ui.common.LogcatLargeColumnScaffold
import de.brudaswen.android.logcat.ui.list.ui.dateTimeFormat
import de.brudaswen.android.logcat.ui.list.ui.icon
import de.brudaswen.android.logcat.ui.theme.LogcatPreviewTheme
import de.brudaswen.android.logcat.ui.theme.LogcatTheme
import de.brudaswen.android.logcat.ui.theme.debugColors
import de.brudaswen.android.logcat.ui.theme.errorColors
import de.brudaswen.android.logcat.ui.theme.fatalColors
import de.brudaswen.android.logcat.ui.theme.infoColors
import de.brudaswen.android.logcat.ui.theme.verboseColors
import de.brudaswen.android.logcat.ui.theme.warningColors
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toInstant

@Composable
internal fun LogcatDetails(
    item: LogcatItemDto?,
    onCopyToClipboardClick: () -> Unit,
    onUpClick: () -> Unit,
) {
    LogcatLargeColumnScaffold(
        title = item?.tag,
        subtitle = item?.date?.format(dateTimeFormat),
        actions = {
            IconButton(
                onClick = onCopyToClipboardClick,
            ) {
                Icon(
                    imageVector = Icons.Outlined.ContentCopy,
                    contentDescription = null,
                )
            }
        },
        onUpClick = onUpClick,
    ) {
        if (item != null) {
            Text(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 16.dp,
                ),
                text = item.message,
                style = LogcatTheme.typography.bodyMedium,
            )

            Card(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                ),
                colors = when (item.level) {
                    LogcatLevel.Fatal -> CardDefaults.fatalColors()
                    LogcatLevel.Error -> CardDefaults.errorColors()
                    LogcatLevel.Warning -> CardDefaults.warningColors()
                    LogcatLevel.Info -> CardDefaults.infoColors()
                    LogcatLevel.Debug -> CardDefaults.debugColors()
                    LogcatLevel.Verbose -> CardDefaults.verboseColors()
                    null -> CardDefaults.cardColors()
                },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        item.icon()?.let { icon ->
                            Icon(
                                modifier = Modifier.padding(end = 8.dp),
                                imageVector = icon,
                                contentDescription = null,
                            )
                        }

                        Text(
                            text = item.level.toString(),
                            style = LogcatTheme.typography.bodyLarge,
                        )
                    }

                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = stringResource(R.string.logcat_details_tid, item.tid),
                        style = LogcatTheme.typography.labelLarge,
                    )

                    Text(
                        text = stringResource(R.string.logcat_details_pid, item.pid),
                        style = LogcatTheme.typography.labelLarge,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
internal fun LogcatDetailsPreview() = LogcatPreviewTheme {
    LogcatDetails(
        item = LogcatItemDto(
            uuid = "uuid",
            date = LocalDateTime(2025, 10, 6, 13, 26, 5, 42_123_456).toInstant(TimeZone.UTC),
            pid = 42876,
            tid = 97371,
            level = LogcatLevel.Info,
            tag = "DisplayManager",
            message = "Choreographer implicitly registered for the refresh rate.",
        ),
        onCopyToClipboardClick = {},
        onUpClick = {},
    )
}
