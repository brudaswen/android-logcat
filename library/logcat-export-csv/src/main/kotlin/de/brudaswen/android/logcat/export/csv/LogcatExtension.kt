package de.brudaswen.android.logcat.export.csv

import de.brudaswen.android.logcat.Logcat
import de.brudaswen.android.logcat.export.export
import de.brudaswen.android.logcat.export.share
import java.io.OutputStream

/**
 * Export Logcat as Comma Separated Value file (csv) into [output].
 */
public suspend fun Logcat.exportAsCsv(
    output: OutputStream,
    pageSize: Int = 50,
) {
    export(
        serializer = LogcatCsvSerializer,
        output = output,
        pageSize = pageSize,
    )
}

/**
 * Share Logcat as Comma Separated Value file (csv).
 */
public suspend fun Logcat.shareAsCsv(
    filename: String = "logcat.csv",
    pageSize: Int = 50,
) {
    share(
        serializer = LogcatCsvSerializer,
        filename = filename,
        pageSize = pageSize,
    )
}
