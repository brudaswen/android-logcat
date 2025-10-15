package de.brudaswen.android.logcat.ui.list.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
internal fun LogcatListMenu(
    onOpenMenuClick: () -> Unit,
) {
    IconButton(
        onClick = onOpenMenuClick,
    ) {
        Icon(
            imageVector = Icons.Outlined.MoreVert,
            contentDescription = null,
        )
    }
}
