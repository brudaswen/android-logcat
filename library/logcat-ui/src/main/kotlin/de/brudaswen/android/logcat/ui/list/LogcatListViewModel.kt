package de.brudaswen.android.logcat.ui.list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import de.brudaswen.android.logcat.Logcat
import de.brudaswen.android.logcat.export.csv.shareAsCsv
import de.brudaswen.android.logcat.export.shareAsTxt
import kotlinx.coroutines.launch

internal class LogcatListViewModel(
    context: Context,
) : ViewModel() {

    private val logcat = Logcat(context)

    val logcatItems = Pager(
        config = PagingConfig(
            pageSize = 50,
        ),
        pagingSourceFactory = logcat.searchDao::getAllPaged
    ).flow.cachedIn(viewModelScope)

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
}
