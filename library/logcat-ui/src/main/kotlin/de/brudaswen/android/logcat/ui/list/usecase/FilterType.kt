package de.brudaswen.android.logcat.ui.list.usecase

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BookmarkRemove
import androidx.compose.material.icons.outlined.CommentsDisabled
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import de.brudaswen.android.logcat.database.LogcatFilter
import de.brudaswen.android.logcat.database.LogcatFilter.QueryPrecision.Contains
import de.brudaswen.android.logcat.database.LogcatFilter.QueryPrecision.Exactly
import de.brudaswen.android.logcat.ui.R
import de.brudaswen.android.logcat.ui.list.usecase.FilterType.ExcludeMessageContains
import de.brudaswen.android.logcat.ui.list.usecase.FilterType.ExcludeMessageExactly
import de.brudaswen.android.logcat.ui.list.usecase.FilterType.ExcludeTagContains
import de.brudaswen.android.logcat.ui.list.usecase.FilterType.ExcludeTagExactly
import de.brudaswen.android.logcat.ui.list.usecase.FilterType.IncludeMessageContains
import de.brudaswen.android.logcat.ui.list.usecase.FilterType.IncludeMessageExactly
import de.brudaswen.android.logcat.ui.list.usecase.FilterType.IncludeTagContains
import de.brudaswen.android.logcat.ui.list.usecase.FilterType.IncludeTagExactly
import de.brudaswen.android.logcat.ui.list.usecase.FilterType.Search

internal enum class FilterType(
    val prefix: String,
) {
    Search("all:"),
    IncludeTagContains("tag:"),
    IncludeTagExactly("tag=:"),
    ExcludeTagContains("-tag:"),
    ExcludeTagExactly("-tag=:"),
    IncludeMessageContains("message:"),
    IncludeMessageExactly("message=:"),
    ExcludeMessageContains("-message:"),
    ExcludeMessageExactly("-message=:"),
}

internal fun FilterType.query(keyword: String) = "$prefix$keyword"

internal val FilterType.icon
    get() = when (this) {
        Search -> Icons.Default.Search
        IncludeTagContains -> Icons.Filled.Bookmark
        IncludeTagExactly -> Icons.Filled.Bookmark
        ExcludeTagContains -> Icons.Outlined.BookmarkRemove
        ExcludeTagExactly -> Icons.Outlined.BookmarkRemove
        IncludeMessageContains -> Icons.AutoMirrored.Filled.Comment
        IncludeMessageExactly -> Icons.AutoMirrored.Filled.Comment
        ExcludeMessageContains -> Icons.Outlined.CommentsDisabled
        ExcludeMessageExactly -> Icons.Outlined.CommentsDisabled
    }

@Composable
internal fun FilterType.description() = when (this) {
    Search -> stringResource(R.string.logcat_filter_type_search_description)
    IncludeTagContains -> stringResource(R.string.logcat_filter_type_include_tag_contains_description)
    IncludeTagExactly -> stringResource(R.string.logcat_filter_type_include_tag_exactly_description)
    ExcludeTagContains -> stringResource(R.string.logcat_filter_type_exclude_tag_contains_description)
    ExcludeTagExactly -> stringResource(R.string.logcat_filter_type_exclude_tag_exactly_description)
    IncludeMessageContains -> stringResource(R.string.logcat_filter_type_include_message_contains_description)
    IncludeMessageExactly -> stringResource(R.string.logcat_filter_type_include_message_exactly_description)
    ExcludeMessageContains -> stringResource(R.string.logcat_filter_type_exclude_message_contains_description)
    ExcludeMessageExactly -> stringResource(R.string.logcat_filter_type_exclude_message_exactly_description)
}

@Composable
internal fun FilterType.description(keyword: String) = when (this) {
    Search -> stringResource(R.string.logcat_filter_search_description, keyword)
    IncludeTagContains -> stringResource(R.string.logcat_filter_include_tag_contains_description, keyword)
    IncludeTagExactly -> stringResource(R.string.logcat_filter_include_tag_exactly_description, keyword)
    ExcludeTagContains -> stringResource(R.string.logcat_filter_exclude_tag_contains_description, keyword)
    ExcludeTagExactly -> stringResource(R.string.logcat_filter_exclude_tag_exactly_description, keyword)
    IncludeMessageContains -> stringResource(R.string.logcat_filter_include_message_contains_description, keyword)
    IncludeMessageExactly -> stringResource(R.string.logcat_filter_include_message_exactly_description, keyword)
    ExcludeMessageContains -> stringResource(R.string.logcat_filter_exclude_message_contains_description, keyword)
    ExcludeMessageExactly -> stringResource(R.string.logcat_filter_exclude_message_exactly_description, keyword)
}

internal fun FilterType.toLogcatFilter(keyword: String) = when (this) {
    Search -> LogcatFilter.Search(keyword)
    IncludeTagContains -> LogcatFilter.Tag.Include(keyword, Contains)
    IncludeTagExactly -> LogcatFilter.Tag.Include(keyword, Exactly)
    ExcludeTagContains -> LogcatFilter.Tag.Exclude(keyword, Contains)
    ExcludeTagExactly -> LogcatFilter.Tag.Exclude(keyword, Exactly)
    IncludeMessageContains -> LogcatFilter.Message.Include(keyword, Contains)
    IncludeMessageExactly -> LogcatFilter.Message.Include(keyword, Exactly)
    ExcludeMessageContains -> LogcatFilter.Message.Exclude(keyword, Contains)
    ExcludeMessageExactly -> LogcatFilter.Message.Exclude(keyword, Exactly)
}
