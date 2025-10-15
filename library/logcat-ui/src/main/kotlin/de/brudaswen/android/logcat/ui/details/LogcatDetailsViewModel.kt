package de.brudaswen.android.logcat.ui.details

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.getSystemService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.brudaswen.android.logcat.Logcat
import de.brudaswen.android.logcat.export.txt.LogcatTxtSerializer
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

internal class LogcatDetailsViewModel(
    context: Context,
    uuid: String,
) : ViewModel() {

    private val logcat = Logcat(context)

    private val clipboard = context.getSystemService<ClipboardManager>()

    val item = logcat.searchDao.getById(uuid)
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    fun onCopyToClipboardClick() {
        val item = item.value ?: return

        val content = buildString {
            LogcatTxtSerializer(this).export(listOf(item))
        }

        clipboard?.setPrimaryClip(ClipData.newPlainText("Logcat", content))
    }
}
