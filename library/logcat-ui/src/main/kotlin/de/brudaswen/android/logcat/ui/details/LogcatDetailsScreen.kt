package de.brudaswen.android.logcat.ui.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import de.brudaswen.android.logcat.ui.details.ui.LogcatDetails

@Composable
public fun LogcatDetailsScreen(
    uuid: String,
    onUpClick: () -> Unit,
) {
    val context = LocalContext.current
    val viewModel = viewModel { LogcatDetailsViewModel(context, uuid) }
    val item by viewModel.item.collectAsStateWithLifecycle()

    LogcatDetails(
        item = item,
        onCopyToClipboardClick = viewModel::onCopyToClipboardClick,
        onUpClick = onUpClick,
    )
}
