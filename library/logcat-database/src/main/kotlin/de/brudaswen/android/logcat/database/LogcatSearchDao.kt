package de.brudaswen.android.logcat.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import de.brudaswen.android.logcat.core.data.LogcatLevel
import kotlinx.coroutines.flow.Flow

/**
 * DAO to search for [LogcatItemDto].
 */
@Dao
public interface LogcatSearchDao {

    /**
     * Get all [LogcatItems][LogcatItemDto].
     */
    @Query("SELECT * FROM logcatItem ORDER BY date DESC")
    public fun getAll(): List<LogcatItemDto>

    /**
     * Get all [LogcatItems][LogcatItemDto] as [PagingSource].
     */
    @Query("SELECT * FROM logcatItem ORDER BY date DESC")
    public fun getAllPaged(): PagingSource<Int, LogcatItemDto>

    /**
     * Count total number of [LogcatItems][LogcatItemDto].
     */
    @Query("SELECT COUNT(*) FROM logcatItem")
    public fun countAll(): Flow<Int>

    /**
     * Get [LogcatItems][LogcatItemDto] by its [uuid][LogcatItemDto.uuid].
     */
    @Query("SELECT * FROM logcatItem WHERE uuid = :uuid")
    public fun getById(uuid: String): Flow<LogcatItemDto>

    /**
     * Search [LogcatItems][LogcatItemDto] as [PagingSource].
     *
     * @param query The search text that should appear in [LogcatItemDto.tag] or [LogcatItemDto.message].
     * @param levels The [LogcatLevel] to filter for.
     */
    @Query(
        """
        SELECT logcatItem.* FROM logcatItem
        WHERE
            (:query IS NULL OR logcatItem.tag LIKE :query OR logcatItem.message LIKE :query) AND
            level IN (:levels)
        ORDER BY date DESC
        """,
    )
    public fun searchPaged(
        query: String?,
        levels: List<LogcatLevel> = LogcatLevel.entries,
    ): PagingSource<Int, LogcatItemDto>

    /**
     * Count total number of [LogcatItems][LogcatItemDto] returned by [searchPaged].
     *
     * @param query The search text that should appear in [LogcatItemDto.tag] or [LogcatItemDto.message].
     * @param levels The [LogcatLevel] to filter for.
     */
    @Query(
        """
        SELECT COUNT(*) FROM (
        SELECT logcatItem.* FROM logcatItem
        WHERE
            (:query IS NULL OR logcatItem.tag LIKE :query OR logcatItem.message LIKE :query) AND
            level IN (:levels)
        ORDER BY date DESC
        )
        """,
    )
    public fun countSearchPaged(
        query: String?,
        levels: List<LogcatLevel> = LogcatLevel.entries,
    ): Flow<Int>

    /**
     * Delete all [LogcatItems][LogcatItemDto].
     */
    @Query("DELETE FROM logcatItem")
    public suspend fun clear()
}
