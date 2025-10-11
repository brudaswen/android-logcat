package de.brudaswen.android.logcat.ui.extension

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems

internal fun <T : Any> LazyListScope.items(
    items: LazyPagingItems<T>,
    key: ((item: T) -> Any)? = null,
    contentType: (item: T) -> Any? = { null },
    itemContent: @Composable LazyItemScope.(item: T) -> Unit,
) = items(
    count = items.itemCount,
    key = when {
        key != null -> { index: Int ->
            items[index]?.let { key(it) } ?: index
        }

        else -> null
    },
    contentType = { index: Int ->
        items[index]?.let {
            contentType(it)
        }
    },
    itemContent = { index: Int ->
        items[index]?.let {
            itemContent(it)
        }
    },
)
