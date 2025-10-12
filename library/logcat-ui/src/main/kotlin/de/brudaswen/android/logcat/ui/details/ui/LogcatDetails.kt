package de.brudaswen.android.logcat.ui.details.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import de.brudaswen.android.logcat.database.LogcatItemDto
import de.brudaswen.android.logcat.ui.common.LogcatLargeColumnScaffold
import de.brudaswen.android.logcat.ui.list.ui.dateTimeFormat
import de.brudaswen.android.logcat.ui.preview.LogcatItemDtoPreviewProvider
import de.brudaswen.android.logcat.ui.theme.LogcatPreviewTheme
import kotlinx.datetime.format

@Composable
internal fun LogcatDetails(
    item: LogcatItemDto?,
    onCopyToClipboardClick: () -> Unit,
    onUpClick: () -> Unit,
) {
    var softWrap by remember { mutableStateOf(true) }

    LogcatLargeColumnScaffold(
        title = item?.tag,
        subtitle = item?.date?.format(dateTimeFormat),
        onUpClick = onUpClick,
    ) {
        if (item != null) {
            LogcatDetailsCard(
                modifier = Modifier.padding(horizontal = 16.dp),
                item = item,
                softWrap = softWrap,
                onSoftWrapClick = { softWrap = !softWrap },
                onCopyToClipboardClick = onCopyToClipboardClick,
            )
        }
    }
}

@Preview
@Composable
internal fun LogcatDetailsPreview(
    @PreviewParameter(LogcatItemDtoPreviewProvider::class)
    item: LogcatItemDto,
) = LogcatPreviewTheme {
    LogcatDetails(
        item = item,
        onCopyToClipboardClick = {},
        onUpClick = {},
    )
}
