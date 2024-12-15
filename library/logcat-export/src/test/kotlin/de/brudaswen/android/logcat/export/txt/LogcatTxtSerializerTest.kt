package de.brudaswen.android.logcat.export.txt

import de.brudaswen.android.logcat.core.data.LogcatItem
import de.brudaswen.android.logcat.core.data.LogcatLevel
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import java.io.StringWriter
import kotlin.test.Test
import kotlin.test.assertEquals

internal class LogcatTxtSerializerTest {
    @Test
    fun `test default txt format`() {
        val items = listOf(
            LogcatItem(
                date = LocalDateTime(2024, 12, 15, 14, 16, 42, 987654321).toInstant(TimeZone.UTC),
                pid = 22483,
                tid = 42,
                level = LogcatLevel.Verbose,
                tag = "JustMyVeryLongJavaLikeLoggingTag",
                message = "Some long message.",
            ),
            LogcatItem(
                date = LocalDateTime(2024, 12, 15, 0, 0, 0, 0).toInstant(TimeZone.UTC),
                pid = 42,
                tid = 22483,
                level = LogcatLevel.Error,
                tag = "JustAWayTooLongJavaLikeLoggingTag",
                message = "Some long message\nwith newline.",
            ),
            LogcatItem(
                date = LocalDateTime(2024, 12, 15, 0, 0, 0, 0).toInstant(TimeZone.UTC),
                pid = 42,
                tid = 22483,
                level = LogcatLevel.Fatal,
                tag = "JustMyWayTooLongJavaLikeLoggingTag",
                message = "Some long message\nwith\nnewlines.",
            ),
        )

        val writer = StringWriter()
        LogcatTxtSerializer(writer).export(items)

        assertEquals(
            """
                |2024-12-15 14:16:42.987 22483-42    [ V ] JustMyVeryLongJavaLikeLoggingTag Some long message.
                |2024-12-15 00:00:00.000    42-22483 [ E ] JustAWayTooLongJ…aLikeLoggingTag Some long message
                |                                                                           with newline.
                |2024-12-15 00:00:00.000    42-22483 [ F ] JustMyWayTooLong…aLikeLoggingTag Some long message
                |                                                                           with
                |                                                                           newlines.
                |
            """.trimMargin(),
            writer.toString(),
        )
    }
}
