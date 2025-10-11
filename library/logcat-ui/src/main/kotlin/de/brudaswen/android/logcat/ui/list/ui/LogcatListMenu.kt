package de.brudaswen.android.logcat.ui.list.ui

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.TextSnippet
import androidx.compose.material.icons.outlined.TableChart
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import de.brudaswen.android.logcat.ui.R
import de.brudaswen.android.logcat.ui.theme.LogcatTheme

@Composable
internal fun LogcatListMenu(
    onExportAsTxtClick: () -> Unit,
    onExportAsCsvClick: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    ModalBottomSheet(
        containerColor = LogcatTheme.colorScheme.surface,
        onDismissRequest = onDismissRequest,
    ) {
        MenuItem(
            text = stringResource(R.string.logcat_list_menu_share_txt_title),
            icon = Icons.AutoMirrored.Outlined.TextSnippet,
            onClick = {
                onExportAsTxtClick()
                onDismissRequest()
            },
        )
        MenuItem(
            text = stringResource(R.string.logcat_list_menu_share_csv_title),
            icon = Icons.Outlined.TableChart,
            onClick = {
                onExportAsCsvClick()
                onDismissRequest()
            },
        )
    }
}

@Composable
private fun MenuItem(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    ListItem(
        modifier = Modifier.clickable(
            onClick = onClick,
        ),
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null,
            )
        },
        headlineContent = {
            Text(
                text = text,
                style = LogcatTheme.typography.bodyMedium,
            )
        },
    )
}
