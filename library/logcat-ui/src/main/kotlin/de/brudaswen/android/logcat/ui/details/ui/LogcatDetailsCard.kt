package de.brudaswen.android.logcat.ui.details.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily.Companion.Monospace
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import de.brudaswen.android.logcat.core.data.LogcatLevel
import de.brudaswen.android.logcat.database.LogcatItemDto
import de.brudaswen.android.logcat.ui.R
import de.brudaswen.android.logcat.ui.extension.runIf
import de.brudaswen.android.logcat.ui.preview.LogcatItemDtoPreviewProvider
import de.brudaswen.android.logcat.ui.theme.LogcatPreviewTheme
import de.brudaswen.android.logcat.ui.theme.LogcatTheme

@Composable
internal fun LogcatDetailsCard(
    modifier: Modifier = Modifier,
    item: LogcatItemDto,
    softWrap: Boolean,
    onSoftWrapClick: () -> Unit,
    onCopyToClipboardClick: () -> Unit,
) {
    OutlinedCard(
        modifier = modifier,
    ) {
        LogcatDetailsInfoCard(
            item = item,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = stringResource(R.string.logcat_details_message),
                style = LogcatTheme.typography.titleMedium,
            )
            Text(
                modifier = Modifier
                    .runIf(!softWrap) {
                        horizontalScroll(rememberScrollState())
                    },
                text = item.message,
                style = LogcatTheme.typography.bodySmall.copy(fontFamily = Monospace),
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
            ) {
                OutlinedButton(
                    onClick = onSoftWrapClick,
                ) {
                    Icon(
                        imageVector = when {
                            softWrap -> ImageVector.vectorResource(R.drawable.format_text_wrap)
                            else -> ImageVector.vectorResource(R.drawable.format_text_overflow)
                        },
                        contentDescription = null,
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = when {
                            softWrap -> stringResource(R.string.logcat_message_soft_wrap)
                            else -> stringResource(R.string.logcat_message_overflow)
                        },
                    )
                }
                FilledTonalButton(
                    onClick = onCopyToClipboardClick,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ContentCopy,
                        contentDescription = null,
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = stringResource(R.string.logcat_copy),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
internal fun LogcatDetailsMessageCardPreview(
    @PreviewParameter(LogcatItemDtoPreviewProvider::class)
    item: LogcatItemDto,
) = LogcatPreviewTheme {
    LogcatDetailsCard(
        item = item,
        softWrap = item.level == LogcatLevel.Info,
        onSoftWrapClick = {},
        onCopyToClipboardClick = {},
    )
}
