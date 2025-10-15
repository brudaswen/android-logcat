package de.brudaswen.android.logcat.ui.details.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import de.brudaswen.android.logcat.core.data.LogcatLevel
import de.brudaswen.android.logcat.database.LogcatItemDto
import de.brudaswen.android.logcat.ui.R
import de.brudaswen.android.logcat.ui.list.ui.icon
import de.brudaswen.android.logcat.ui.preview.LogcatItemDtoPreviewProvider
import de.brudaswen.android.logcat.ui.theme.LogcatPreviewTheme
import de.brudaswen.android.logcat.ui.theme.LogcatTheme
import de.brudaswen.android.logcat.ui.theme.extension.debugColors
import de.brudaswen.android.logcat.ui.theme.extension.errorColors
import de.brudaswen.android.logcat.ui.theme.extension.fatalColors
import de.brudaswen.android.logcat.ui.theme.extension.infoColors
import de.brudaswen.android.logcat.ui.theme.extension.verboseColors
import de.brudaswen.android.logcat.ui.theme.extension.warningColors

@Composable
internal fun LogcatDetailsInfoCard(
    item: LogcatItemDto,
) {
    Card(
        colors = item.level?.cardColors() ?: CardDefaults.cardColors(),
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
                    style = LogcatTheme.typography.titleMedium,
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = stringResource(R.string.logcat_details_tid),
                        style = LogcatTheme.typography.titleMedium,
                    )

                    Text(
                        text = item.tid.toString(),
                        style = LogcatTheme.typography.labelLarge,
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = stringResource(R.string.logcat_details_pid),
                        style = LogcatTheme.typography.titleMedium,
                    )

                    Text(
                        text = item.pid.toString(),
                        style = LogcatTheme.typography.labelLarge,
                    )
                }
            }
        }
    }
}

@Composable
private fun LogcatLevel.cardColors(): CardColors = when (this) {
    LogcatLevel.Fatal -> CardDefaults.fatalColors()
    LogcatLevel.Error -> CardDefaults.errorColors()
    LogcatLevel.Warning -> CardDefaults.warningColors()
    LogcatLevel.Info -> CardDefaults.infoColors()
    LogcatLevel.Debug -> CardDefaults.debugColors()
    LogcatLevel.Verbose -> CardDefaults.verboseColors()
}

@Preview
@Composable
internal fun LogcatDetailsInfoCardPreview(
    @PreviewParameter(LogcatItemDtoPreviewProvider::class)
    item: LogcatItemDto,
) = LogcatPreviewTheme {
    LogcatDetailsInfoCard(
        item = item,
    )
}
