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
import androidx.compose.ui.unit.dp
import de.brudaswen.android.logcat.core.data.LogcatLevel
import de.brudaswen.android.logcat.database.LogcatItemDto
import de.brudaswen.android.logcat.ui.R
import de.brudaswen.android.logcat.ui.list.ui.icon
import de.brudaswen.android.logcat.ui.theme.LogcatTheme
import de.brudaswen.android.logcat.ui.theme.extension.debugColors
import de.brudaswen.android.logcat.ui.theme.extension.errorColors
import de.brudaswen.android.logcat.ui.theme.extension.fatalColors
import de.brudaswen.android.logcat.ui.theme.extension.infoColors
import de.brudaswen.android.logcat.ui.theme.extension.verboseColors
import de.brudaswen.android.logcat.ui.theme.extension.warningColors

@Composable
internal fun LogcatDetailsCard(item: LogcatItemDto) {
    Card(
        modifier = Modifier.padding(
            horizontal = 16.dp,
        ),
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

@Composable
private fun LogcatLevel.cardColors(): CardColors = when (this) {
    LogcatLevel.Fatal -> CardDefaults.fatalColors()
    LogcatLevel.Error -> CardDefaults.errorColors()
    LogcatLevel.Warning -> CardDefaults.warningColors()
    LogcatLevel.Info -> CardDefaults.infoColors()
    LogcatLevel.Debug -> CardDefaults.debugColors()
    LogcatLevel.Verbose -> CardDefaults.verboseColors()
}
