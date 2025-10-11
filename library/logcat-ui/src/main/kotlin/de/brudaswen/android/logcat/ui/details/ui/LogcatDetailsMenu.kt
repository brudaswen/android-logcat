package de.brudaswen.android.logcat.ui.details.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import de.brudaswen.android.logcat.ui.R

@Composable
internal fun LogcatDetailsMenu(
    softWrap: Boolean,
    onSoftWrapClick: () -> Unit,
    onCopyToClipboardClick: () -> Unit,
) {
    IconButton(
        onClick = onSoftWrapClick,
    ) {
        Icon(
            imageVector = when {
                softWrap -> ImageVector.vectorResource(R.drawable.format_text_overflow)
                else -> ImageVector.vectorResource(R.drawable.format_text_wrap)
            },
            contentDescription = null,
        )
    }

    IconButton(
        onClick = onCopyToClipboardClick,
    ) {
        Icon(
            imageVector = Icons.Outlined.ContentCopy,
            contentDescription = null,
        )
    }
}
