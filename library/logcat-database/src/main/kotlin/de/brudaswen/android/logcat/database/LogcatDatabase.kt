package de.brudaswen.android.logcat.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * The Logcat database containing all [LogcatItems][LogcatItemDto].
 */
@Database(
    entities = [LogcatItemDto::class],
    autoMigrations = [],
    version = 1,
)
@TypeConverters(LogcatConverters::class)
public abstract class LogcatDatabase : RoomDatabase() {

    /**
     * DAO to search for [LogcatItemDto].
     */
    public abstract val searchDao: LogcatSearchDao

    /**
     * DAO to import [LogcatItemDto].
     */
    public abstract val importDao: LogcatImportDao

    /**
     * DAO to export [LogcatItemDto].
     */
    public abstract val exportDao: LogcatExportDao
}
