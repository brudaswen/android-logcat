package de.brudaswen.android.logcat.export

import de.brudaswen.android.logcat.core.data.LogcatItem
import java.io.OutputStream

/**
 * Serializer for [LogcatExporter] to export [LogcatItems][LogcatItem].
 */
public interface LogcatSerializer {

    /**
     * Export the given [LogcatItems][LogcatItem] and append them to export output.
     *
     * @param items The items to export.
     */
    public fun export(items: List<LogcatItem>)

    /**
     * Factory to create [LogcatSerializer].
     */
    public fun interface Factory {
        /**
         * Create [LogcatSerializer] for given [OutputStream].
         *
         * @param output The output stream the created serializer writes to.
         */
        public operator fun invoke(output: OutputStream): LogcatSerializer
    }
}
