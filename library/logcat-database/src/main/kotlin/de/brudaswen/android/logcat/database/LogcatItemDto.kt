package de.brudaswen.android.logcat.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import de.brudaswen.android.logcat.core.data.LogcatItem
import de.brudaswen.android.logcat.core.data.LogcatLevel
import de.brudaswen.android.logcat.core.data.uuid
import kotlin.time.Instant

/**
 * Representation of one Logcat line.
 *
 * @param uuid The uuid of the log item.
 * @param date The date of the log item.
 * @param pid The process ID that created the item.
 * @param tid The thread ID that created the item.
 * @param level The log level of the log item.
 * @param tag The tag of the log item.
 * @param message The message of the log item.
 */
@Entity(tableName = "logcatItem")
public data class LogcatItemDto(
    @PrimaryKey val uuid: String,
    override val date: Instant,
    override val pid: Int,
    override val tid: Int,
    override val level: LogcatLevel?,
    override val tag: String,
    override val message: String,
) : LogcatItem

/**
 * Convert this [LogcatItem] to [LogcatItemDto].
 */
public fun LogcatItem.toDto(): LogcatItemDto = LogcatItemDto(
    uuid = uuid.toString(),
    date = date,
    pid = pid,
    tid = tid,
    level = level,
    tag = tag,
    message = message,
)
