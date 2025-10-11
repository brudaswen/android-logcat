package de.brudaswen.android.logcat.ui.details.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily.Companion.Monospace
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.brudaswen.android.logcat.core.data.LogcatLevel
import de.brudaswen.android.logcat.database.LogcatItemDto
import de.brudaswen.android.logcat.ui.common.LogcatLargeColumnScaffold
import de.brudaswen.android.logcat.ui.extension.runIf
import de.brudaswen.android.logcat.ui.list.ui.dateTimeFormat
import de.brudaswen.android.logcat.ui.theme.LogcatPreviewTheme
import de.brudaswen.android.logcat.ui.theme.LogcatTheme
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
    var softWrap by remember { mutableStateOf(false) }

    LogcatLargeColumnScaffold(
        title = item?.tag,
        subtitle = item?.date?.format(dateTimeFormat),
        actions = {
            LogcatDetailsMenu(
                softWrap = softWrap,
                onSoftWrapClick = { softWrap = !softWrap },
                onCopyToClipboardClick = onCopyToClipboardClick,
            )
        },
        onUpClick = onUpClick,
    ) {
        if (item != null) {
            LogcatDetailsCard(
                item = item,
            )

            Text(
                modifier = Modifier
                    .runIf(softWrap) {
                        horizontalScroll(rememberScrollState())
                    }
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp),
                text = item.message,
                style = LogcatTheme.typography.bodySmall.copy(fontFamily = Monospace),
            )
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
