package de.brudaswen.android.logcat.export

import android.content.Intent
import androidx.core.content.FileProvider
import de.brudaswen.android.logcat.Logcat
import de.brudaswen.android.logcat.export.txt.LogcatTxtSerializer
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

/**
 * Export Logcat using given [serializer] into [output].
 */
public suspend fun Logcat.export(
    serializer: LogcatSerializer.Factory,
    output: OutputStream,
    pageSize: Int = 50,
) {
    LogcatExporter(
        dao = exportDao,
        serializer = serializer,
    ).export(
        output = output,
        pageSize = pageSize,
    )
}

/**
 * Export Logcat as text file (txt) into [output].
 */
public suspend fun Logcat.exportAsTxt(
    output: OutputStream,
    pageSize: Int = 50,
) {
    export(
        serializer = LogcatTxtSerializer,
        output = output,
        pageSize = pageSize,
    )
}

/**
 * Share Logcat using [serializer] as file with [filename].
 */
public suspend fun Logcat.share(
    serializer: LogcatSerializer.Factory,
    filename: String,
    pageSize: Int = 50,
) {
    val exportFile = getExportFile(filename)

    export(
        serializer = serializer,
        output = FileOutputStream(exportFile),
        pageSize = pageSize,
    )

    shareFile(exportFile)
}

/**
 * Share Logcat as text file (txt).
 */
public suspend fun Logcat.shareAsTxt(
    filename: String = "logcat.txt",
    pageSize: Int = 50,
) {
    share(
        serializer = LogcatTxtSerializer,
        filename = filename,
        pageSize = pageSize,
    )
}

private fun Logcat.shareFile(file: File) {
    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.logcat.export",
        file,
    )
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_STREAM, uri)
    }
    context.startActivity(
        Intent.createChooser(shareIntent, null).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        },
    )
}

private fun Logcat.getExportFile(filename: String): File =
    File(context.cacheDir, "logcat-export/$filename").apply {
        parentFile?.mkdirs()
    }
