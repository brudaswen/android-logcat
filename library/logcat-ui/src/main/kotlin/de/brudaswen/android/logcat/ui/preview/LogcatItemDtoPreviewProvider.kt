package de.brudaswen.android.logcat.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import de.brudaswen.android.logcat.core.data.LogcatLevel
import de.brudaswen.android.logcat.database.LogcatItemDto
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

internal class LogcatItemDtoPreviewProvider : PreviewParameterProvider<LogcatItemDto> {
    override val values = sequenceOf(
        LogcatItemDto(
            uuid = "uuid",
            date = LocalDateTime(
                2025,
                10,
                6,
                13,
                26,
                5,
                42_123_456,
            ).toInstant(TimeZone.Companion.UTC),
            pid = 42876,
            tid = 97371,
            level = LogcatLevel.Info,
            tag = "DisplayManager",
            message = "Choreographer implicitly registered for the refresh rate.",
        ),
        LogcatItemDto(
            uuid = "uuid",
            date = LocalDateTime(
                2025,
                10,
                6,
                13,
                26,
                5,
                42_123_456,
            ).toInstant(TimeZone.Companion.UTC),
            pid = 22409,
            tid = 22428,
            level = LogcatLevel.Error,
            tag = "InteractionJankMonitor",
            message = """Initializing without READ_DEVICE_CONFIG permission.
                |
                |enabled=false
                |interval=1
                |missedFrameThreshold=3
                |frameTimeThreshold=64
                |package=de.brudaswen.android.logcat.app
            """.trimMargin(),
        ),
    )
}
