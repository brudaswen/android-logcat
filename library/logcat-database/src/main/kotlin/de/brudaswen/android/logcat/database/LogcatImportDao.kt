package de.brudaswen.android.logcat.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.brudaswen.android.logcat.core.data.LogcatItem
import kotlinx.datetime.Instant

/**
 * DAO to import [LogcatItemDto].
 */
@Dao
public interface LogcatImportDao {

    /**
     * Find largest [LogcatItemDto.date] in the database.
     */
    @Query("SELECT MAX(date) FROM logcatItem")
    public fun maxDate(): Instant?

    /**
     * Insert multiple [LogcatItems][LogcatItemDto] into the database.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public suspend fun insertAll(vararg items: LogcatItemDto)

    public suspend fun insertAll(vararg items: LogcatItem): Unit =
        insertAll(items = items.map(LogcatItem::toDto).toTypedArray())

    /**
     * Delete all [LogcatItems][LogcatItemDto] before the given [date].
     */
    @Query("DELETE FROM logcatItem WHERE date < :date")
    public suspend fun clearBefore(date: Instant)

    /**
     * Delete all [LogcatItems][LogcatItemDto].
     */
    @Query("DELETE FROM logcatItem")
    public suspend fun clear()
}
