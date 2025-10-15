package de.brudaswen.android.logcat.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.RoomRawQuery
import de.brudaswen.android.logcat.core.data.LogcatLevel
import kotlinx.coroutines.flow.Flow

/**
 * DAO to search for [LogcatItemDto].
 */
@Dao
public abstract class LogcatSearchDao {

    /**
     * Get all [LogcatItems][LogcatItemDto].
     */
    @Query("SELECT * FROM logcatItem ORDER BY date DESC")
    public abstract fun getAll(): List<LogcatItemDto>

    /**
     * Get all [LogcatItems][LogcatItemDto] as [PagingSource].
     */
    @Query("SELECT * FROM logcatItem ORDER BY date DESC")
    public abstract fun getAllPaged(): PagingSource<Int, LogcatItemDto>

    /**
     * Count total number of [LogcatItems][LogcatItemDto].
     */
    @Query("SELECT COUNT(*) FROM logcatItem")
    public abstract fun countAll(): Flow<Int>

    /**
     * Get [LogcatItems][LogcatItemDto] by its [uuid][LogcatItemDto.uuid].
     */
    @Query("SELECT * FROM logcatItem WHERE uuid = :uuid")
    public abstract fun getById(uuid: String): Flow<LogcatItemDto>

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
    public abstract fun searchPaged(
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
    public abstract fun countSearchPaged(
        query: String?,
        levels: List<LogcatLevel> = LogcatLevel.entries,
    ): Flow<Int>

    /**
     * Search [LogcatItems][LogcatItemDto] as [PagingSource].
     *
     * @param filters Multiple [LogcatFilters][LogcatFilter] can be used to filter the search.
     */
    public fun searchPaged(
        filters: List<LogcatFilter>,
    ): PagingSource<Int, LogcatItemDto> {
        val conditions = filters
            .groupBy { it.orGroup }
            .values
            .map { SqlCondition.Or(it) }
            .takeIf { it.isNotEmpty() }

        val where = conditions?.joinToString(
            prefix = "WHERE ",
            separator = " AND ",
            transform = SqlCondition::sql,
        ).orEmpty()

        return rawSearchPaged(
            query = RoomRawQuery(
                sql = """
                    SELECT logcatItem.* FROM logcatItem
                    $where
                    ORDER BY date DESC
                """.trimIndent(),
                onBindStatement = { statement ->
                    var index = 1
                    conditions?.forEach {
                        index = it.bind(index, statement)
                    }
                },
            ),
        )
    }

    /**
     * Search for [tag][LogcatItemDto.tag] that match (contain) given [query].
     *
     * Results are sorted in ascending order, but tags starting with [query] are returned first
     * and tags containing [query] afterwards.
     */
    @Query(
        """
        SELECT DISTINCT tag FROM logcatItem 
        WHERE tag LIKE ('%' || :query || '%') 
        ORDER BY CASE WHEN tag LIKE (:query || '%') THEN 0 ELSE 1 END, tag ASC 
        """,
    )
    public abstract suspend fun searchTags(query: String): List<String>

    /**
     * Delete all [LogcatItems][LogcatItemDto].
     */
    @Query("DELETE FROM logcatItem")
    public abstract suspend fun clear()

    @RawQuery(observedEntities = [LogcatItemDto::class])
    protected abstract fun rawSearchPaged(query: RoomRawQuery): PagingSource<Int, LogcatItemDto>
}
