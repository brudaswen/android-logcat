package de.brudaswen.android.logcat.export.csv

import de.brudaswen.android.logcat.core.data.LogcatItem
import de.brudaswen.android.logcat.core.data.LogcatLevel
import de.brudaswen.android.logcat.export.LogcatSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.csv.Csv
import kotlinx.serialization.csv.CsvRecordWriter
import kotlinx.serialization.csv.recordWriter
import java.io.OutputStream
import java.io.Writer
import kotlin.time.Instant

/**
 * [LogcatSerializer] in CSV format.
 */
public class LogcatCsvSerializer private constructor(
    private val writer: CsvRecordWriter<CsvLogcatItem>,
) : LogcatSerializer {

    public constructor(
        csv: Csv = Csv,
        output: Writer,
    ) : this(
        writer = csv.recordWriter(CsvLogcatItem.serializer(), output),
    )

    override fun export(items: List<LogcatItem>) {
        items.forEach {
            writer.write(
                CsvLogcatItem(
                    date = it.date,
                    pid = it.pid,
                    tid = it.tid,
                    level = it.level,
                    tag = it.tag,
                    message = it.message,
                ),
            )
        }
    }

    /**
     * Default [LogcatSerializer.Factory] for [LogcatCsvSerializer].
     *
     * Use [LogcatCsvSerializer.with] to provide custom [Csv] format.
     */
    public companion object Factory : LogcatSerializer.Factory {
        override operator fun invoke(output: OutputStream): LogcatSerializer =
            LogcatCsvSerializer(output = output.writer())

        /**
         * Create [LogcatSerializer.Factory] with custom [Csv] format.
         *
         * @param csv The [Csv] format to use during export.
         */
        public fun with(csv: Csv): LogcatSerializer.Factory = LogcatSerializer.Factory {
            LogcatCsvSerializer(
                csv = csv,
                output = it.writer(),
            )
        }
    }
}

/**
 * The Logcat item that defines [Csv] line.
 */
@Serializable
private data class CsvLogcatItem(
    @SerialName("Date") val date: Instant,
    @SerialName("PID") val pid: Int,
    @SerialName("TID") val tid: Int,
    @SerialName("Level") val level: LogcatLevel?,
    @SerialName("Tag") val tag: String,
    @SerialName("Message") val message: String,
)
