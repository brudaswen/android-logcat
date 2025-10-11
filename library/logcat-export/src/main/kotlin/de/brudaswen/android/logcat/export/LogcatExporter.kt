package de.brudaswen.android.logcat.export

import de.brudaswen.android.logcat.database.LogcatExportDao
import de.brudaswen.android.logcat.database.LogcatItemDto
import de.brudaswen.android.logcat.export.txt.LogcatTxtSerializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import java.io.OutputStream

/**
 * Exporter to export all [LogcatItems][LogcatItemDto].
 *
 * @param dao The export DAO.
 * @param serializer The [LogcatSerializer] that defines the output format.
 */
public class LogcatExporter(
    private val dao: LogcatExportDao,
    private val serializer: LogcatSerializer.Factory = LogcatTxtSerializer,
) {

    /**
     * Export all [LogcatItems][LogcatItemDto] to given [output].
     *
     * @param output The output stream for the export.
     * @param pageSize The number of items per page to load from the database.
     */
    public suspend fun export(
        output: OutputStream,
        pageSize: Int = 50,
    ): Unit = Dispatchers.IO {
        val serializer = serializer(output)

        dao.exportAll(
            pageSize = pageSize,
            action = serializer::export,
        )
    }
}
