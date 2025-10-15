package de.brudaswen.android.logcat.ui.component.scaffold.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.pinnedScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import de.brudaswen.android.logcat.core.data.LogcatLevel
import de.brudaswen.android.logcat.database.LogcatFilter
import de.brudaswen.android.logcat.ui.list.usecase.Suggestion

@Composable
internal fun LogcatSearchScaffold(
    textFieldState: TextFieldState,
    itemCount: Int,
    suggestions: List<Suggestion>,
    filters: List<LogcatFilter>,
    onSearch: (String) -> Unit,
    onLevelSelectionChanged: (LogcatLevel, Boolean) -> Unit,
    onAddFilterClick: (LogcatFilter) -> Unit,
    onRemoveFilterClick: (LogcatFilter) -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    onUpClick: (() -> Unit)?,
    content: @Composable (PaddingValues) -> Unit,
) {
    val scrollBehavior = pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    LogcatSearchBar(
                        textFieldState = textFieldState,
                        itemCount = itemCount,
                        suggestions = suggestions,
                        filters = filters,
                        onSearch = onSearch,
                        onAddFilterClick = onAddFilterClick,
                        onRemoveFilterClick = onRemoveFilterClick,
                        onLevelSelectionChanged = onLevelSelectionChanged,
                    )
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
        content = content,
    )
}
