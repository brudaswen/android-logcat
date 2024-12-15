package de.brudaswen.android.logcat.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import de.brudaswen.android.logcat.core.data.LogcatItem

/**
 * DAO to export [LogcatItemDto].
 */
@Dao
public interface LogcatExportDao {

    /**
     * Load one page of [LogcatItems][LogcatItemDto] for export and ascending order.
     *
     * @param offset The start item offset.
     * @param limit The number of items to load.
     */
    @Query("SELECT * FROM logcatItem ORDER BY date ASC LIMIT :limit OFFSET :offset")
    public fun export(offset: Int, limit: Int): List<LogcatItemDto>

    /**
     * Export all [LogcatItems][LogcatItem] by loading them in small chunks.
     *
     * @param pageSize The number of items per page to load.
     * @param action The export action to execute for each loaded page.
     */
    @Transaction
    public fun exportAll(pageSize: Int = 50, action: (List<LogcatItem>) -> Unit) {
        var offset = 0
        do {
            val result = export(offset = offset, limit = pageSize)
            offset += result.size

            action(result)
        } while (result.isNotEmpty())
    }
}
