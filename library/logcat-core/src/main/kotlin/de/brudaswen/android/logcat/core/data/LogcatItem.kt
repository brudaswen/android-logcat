package de.brudaswen.android.logcat.core.data

import de.brudaswen.android.logcat.core.uuid.v5
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents.Formats.ISO_DATE_TIME_OFFSET
import kotlin.time.Instant
import kotlin.uuid.Uuid

private val LOGCAT_NAMESPACE = Uuid.parse("1a8aec4b-880b-4d8a-be1b-4fae5a869f5a")

/**
 * Representation of one Logcat line.
 */
public interface LogcatItem {
    /** The date of the log item. */
    public val date: Instant

    /** The process ID that created the item. */
    public val pid: Int

    /** The thread ID that created the item. */
    public val tid: Int

    /** The log level of the log item. */
    public val level: LogcatLevel?

    /** The tag of the log item. */
    public val tag: String

    /** The message of the log item. */
    public val message: String
}

/**
 * The [Uuid] of the log item based on its data.
 */
public val LogcatItem.uuid: Uuid
    get() = Uuid.v5(
        namespace = LOGCAT_NAMESPACE,
        name = buildString {
            append(date.format(ISO_DATE_TIME_OFFSET))
            append("\n")
            append(pid)
            append("\n")
            append(tid)
            append("\n")
            append(level?.identifier.orEmpty())
            append("\n")
            append(tag)
            append("\n")
            append(message)
        },
    )

/**
 * Representation of one Logcat line.
 *
 * @param date The date of the log item.
 * @param pid The process ID that created the item.
 * @param tid The thread ID that created the item.
 * @param level The log level of the log item.
 * @param tag The tag of the log item.
 * @param message The message of the log item.
 */
public fun LogcatItem(
    date: Instant,
    pid: Int,
    tid: Int,
    level: LogcatLevel?,
    tag: String,
    message: String,
): LogcatItem = LogcatItemImpl(
    date = date,
    pid = pid,
    tid = tid,
    level = level,
    tag = tag,
    message = message,
)

private data class LogcatItemImpl(
    override val date: Instant,
    override val pid: Int,
    override val tid: Int,
    override val level: LogcatLevel?,
    override val tag: String,
    override val message: String,
) : LogcatItem
