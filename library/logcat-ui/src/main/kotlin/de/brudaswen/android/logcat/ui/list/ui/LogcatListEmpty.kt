package de.brudaswen.android.logcat.ui.list.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SettingsBackupRestore
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.brudaswen.android.logcat.ui.R
import de.brudaswen.android.logcat.ui.theme.LogcatPreviewTheme
import de.brudaswen.android.logcat.ui.theme.LogcatTheme

@Composable
internal fun LogcatListEmpty(
    onResetFiltersClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "🦥",
            fontSize = 128.sp,
            textAlign = TextAlign.Center,
        )

        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = stringResource(R.string.logcat_list_empty_title),
            style = LogcatTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = stringResource(R.string.logcat_list_empty_text),
            style = LogcatTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
        )

        Button(
            modifier = Modifier.padding(top = 8.dp),
            onClick = onResetFiltersClick,
        ) {
            Icon(
                imageVector = Icons.Default.SettingsBackupRestore,
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = stringResource(R.string.logcat_reset_filters),
            )
        }
    }
}

@Preview
@Composable
internal fun LogcatListEmptyPreview() = LogcatPreviewTheme {
    LogcatListEmpty(
        onResetFiltersClick = {},
    )
}
