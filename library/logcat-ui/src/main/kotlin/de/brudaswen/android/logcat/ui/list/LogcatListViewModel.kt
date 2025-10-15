package de.brudaswen.android.logcat.ui.list

import android.content.Context
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import de.brudaswen.android.logcat.Logcat
import de.brudaswen.android.logcat.core.data.LogcatLevel
import de.brudaswen.android.logcat.database.LogcatFilter
import de.brudaswen.android.logcat.export.csv.shareAsCsv
import de.brudaswen.android.logcat.export.shareAsTxt
import de.brudaswen.android.logcat.ui.list.usecase.LogcatSuggestionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class LogcatListViewModel(
    context: Context,
) : ViewModel() {

    private val logcat = Logcat(context)

    private val suggestionSearch = LogcatSuggestionUseCase(
        searchDao = logcat.searchDao,
    )

    val textFieldState = TextFieldState()

    val suggestions = snapshotFlow {
        textFieldState.text.toString()
    }.map { input ->
        suggestionSearch(input)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val filters = MutableStateFlow<List<LogcatFilter>>(initialFilters)

    val logcatItems = filters.flatMapLatest { filters ->
        Pager(
            config = PagingConfig(
                pageSize = 50,
            ),
            pagingSourceFactory = {
                logcat.searchDao.searchPaged(filters)
            },
        ).flow
    }.cachedIn(viewModelScope)

    fun onSearch(query: String) {
        filters.value += suggestionSearch.parseLogcatFilter(query)
    }

    fun onLevelSelectionChanged(level: LogcatLevel, selected: Boolean) {
        if (selected) {
            filters.value += LogcatFilter.Level(level)
        } else {
            filters.value -= LogcatFilter.Level(level)
        }
    }

    fun onAddFilterClick(query: LogcatFilter) {
        filters.value += query
    }

    fun onRemoveFilterClick(query: LogcatFilter) {
        filters.value -= query
    }

    fun onExportAsTxtClick() {
        viewModelScope.launch {
            logcat.shareAsTxt()
        }
    }

    fun onExportAsCsvClick() {
        viewModelScope.launch {
            logcat.shareAsCsv()
        }
    }

    fun onResetFiltersClick() {
        filters.value = initialFilters
    }

    fun onResetDatabaseClick() {
        viewModelScope.launch {
            logcat.searchDao.clear()
        }
    }

    companion object {
        private val initialFilters = LogcatLevel.entries.map { LogcatFilter.Level(it) }
    }
}
