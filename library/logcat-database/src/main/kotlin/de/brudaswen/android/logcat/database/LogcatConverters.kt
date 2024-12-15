package de.brudaswen.android.logcat.database

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

/**
 * Database conversion helpers.
 */
internal class LogcatConverters {
    @TypeConverter
    fun toInstant(value: Long?): Instant? =
        value?.let(Instant::fromEpochMilliseconds)

    @TypeConverter
    fun fromInstant(date: Instant?): Long? =
        date?.toEpochMilliseconds()
}
