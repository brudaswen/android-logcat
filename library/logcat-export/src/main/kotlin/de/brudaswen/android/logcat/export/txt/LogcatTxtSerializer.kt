package de.brudaswen.android.logcat.export.txt

import de.brudaswen.android.logcat.core.data.LogcatItem
import de.brudaswen.android.logcat.export.LogcatSerializer
import de.brudaswen.android.logcat.export.extension.truncateMiddle
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.DateTimeComponents.Companion.Format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.char
import java.io.OutputStream

/**
 * [LogcatSerializer] in `txt` format.
 */
public class LogcatTxtSerializer(
    private val writer: Appendable,
    private val format: (LogcatItem) -> String = {
        String.format(
            "%s %5.5s-%-5.5s [ %1.1s ] %-32s %s",
            it.date.format(TXT_EXPORT_DATE_FORMAT),
            it.pid.toString(),
            it.tid.toString(),
            it.level?.name?.uppercase() ?: "?",
            it.tag.truncateMiddle(32),
            it.message.replace("\n", "\n" + " ".repeat(75)),
        )
    },
) : LogcatSerializer {

    override fun export(items: List<LogcatItem>) {
        items.forEach {
            writer.appendLine(format(it))
        }
    }

    /**
     * Default [LogcatSerializer.Factory] for [LogcatTxtSerializer].
     *
     * Use [LogcatTxtSerializer.with] to provide custom `txt` format.
     */
    public companion object Factory : LogcatSerializer.Factory {
        override operator fun invoke(output: OutputStream): LogcatSerializer =
            LogcatTxtSerializer(writer = output.writer())

        /**
         * Create [LogcatSerializer.Factory] with custom `txt` format.
         */
        public fun with(
            format: (LogcatItem) -> String,
        ): LogcatSerializer.Factory = LogcatSerializer.Factory {
            LogcatTxtSerializer(
                writer = it.writer(),
                format = format,
            )
        }

        public val TXT_EXPORT_DATE_FORMAT: DateTimeFormat<DateTimeComponents>
            get() = Format {
                year()
                char('-')
                monthNumber()
                char('-')
                day()
                char(' ')
                hour()
                char(':')
                minute()
                char(':')
                second()
                char('.')
                secondFraction(3, 3)
            }
    }
}
