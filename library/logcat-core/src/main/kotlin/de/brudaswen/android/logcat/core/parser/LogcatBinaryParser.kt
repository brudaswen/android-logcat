package de.brudaswen.android.logcat.core.parser

import de.brudaswen.android.logcat.core.data.LogcatItem
import de.brudaswen.android.logcat.core.data.LogcatLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import kotlinx.io.Buffer
import kotlinx.io.readByteArray
import kotlinx.io.readIntLe
import kotlinx.io.readString
import kotlinx.io.readUShortLe
import kotlinx.io.write
import java.io.Closeable
import java.io.InputStream
import kotlin.time.Instant

/**
 * Logcat parser that parses binary output of `adb logcat --binary`.
 *
 * @param input The input stream to parse.
 */
public class LogcatBinaryParser(
    private val input: InputStream,
) : Closeable by input {

    private val buffer = Buffer()

    /**
     * Parse one [LogcatItem] from the current [input] stream.
     *
     * @return The parsed [LogcatItem] or `null` if stream reached EOF.
     */
    public suspend fun parseItem(): LogcatItem? = Dispatchers.IO {
        val firstByte = input.read()
        if (firstByte == -1) return@IO null

        // Read v1 header
        buffer.writeByte(firstByte.toByte())
        buffer.write(input = input, byteCount = 19)

        val len = buffer.readUShortLe().toInt()
        val headerSize = buffer.readUShortLe().toInt()
        val pid = buffer.readIntLe()
        val tid = buffer.readIntLe()
        val sec = buffer.readIntLe()
        val nsec = buffer.readIntLe()

        // Read additional header fields
        buffer.write(input = input, byteCount = headerSize - 20L)

        val additionalHeaderBytes = (headerSize - 20).coerceAtLeast(0)
        buffer.readByteArray(byteCount = additionalHeaderBytes)

        // Read payload
        buffer.write(input = input, byteCount = len.toLong())

        val priority = buffer.readByte()

        val payload = buffer.readString()
        val texts = payload.split('\u0000', limit = 2)
        val tag = texts.getOrNull(0).orEmpty()
        val message = texts.getOrNull(1).orEmpty().removeSuffix("\u0000").trim()

        // Clear buffer
        buffer.clear()

        // Convert raw values to item
        LogcatItem(
            sec = sec,
            nsec = nsec,
            priority = priority,
            pid = pid,
            tid = tid,
            tag = tag,
            message = message,
        )
    }

    private fun LogcatItem(
        sec: Int,
        nsec: Int,
        priority: Byte,
        pid: Int,
        tid: Int,
        tag: String,
        message: String,
    ): LogcatItem {
        val date = Instant.fromEpochSeconds(
            epochSeconds = sec.toLong(),
            nanosecondAdjustment = nsec,
        )

        val level = when (priority) {
            2.toByte() -> LogcatLevel.Verbose
            3.toByte() -> LogcatLevel.Debug
            4.toByte() -> LogcatLevel.Info
            5.toByte() -> LogcatLevel.Warning
            6.toByte() -> LogcatLevel.Error
            7.toByte() -> LogcatLevel.Fatal
            else -> null
        }

        return LogcatItem(
            date = date,
            pid = pid,
            tid = tid,
            level = level,
            tag = tag,
            message = message,
        )
    }
}
