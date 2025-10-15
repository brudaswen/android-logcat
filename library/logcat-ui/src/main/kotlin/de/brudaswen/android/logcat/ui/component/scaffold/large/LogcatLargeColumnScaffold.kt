package de.brudaswen.android.logcat.ui.component.scaffold.large

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.exitUntilCollapsedScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.brudaswen.android.logcat.ui.theme.LogcatPreviewTheme
import de.brudaswen.android.logcat.ui.theme.LogcatTheme

@Composable
internal fun LogcatLargeColumnScaffold(
    title: String?,
    subtitle: String?,
    actions: @Composable RowScope.() -> Unit = {},
    onUpClick: (() -> Unit)?,
    content: @Composable ColumnScope.() -> Unit,
) {
    val scrollBehavior = exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        if (title != null) {
                            Text(
                                text = title,
                            )
                        }
                        if (subtitle != null) {
                            Text(
                                text = subtitle,
                                style = LogcatTheme.typography.titleSmall,
                            )
                        }
                    }
                },
                navigationIcon = {
                    if (onUpClick != null) {
                        IconButton(
                            onClick = onUpClick,
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBackIos,
                                contentDescription = null,
                            )
                        }
                    }
                },
                actions = actions,
                scrollBehavior = scrollBehavior,
            )
        },
        content = { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(contentPadding)
                    .padding(vertical = 16.dp),
                content = content,
            )
        },
    )
}

@Preview
@Composable
internal fun LogcatLargeColumnScaffoldPreview() = LogcatPreviewTheme {
    LogcatLargeColumnScaffold(
        title = "Title",
        subtitle = "Subtitle",
        onUpClick = {},
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Content",
        )
    }
}
