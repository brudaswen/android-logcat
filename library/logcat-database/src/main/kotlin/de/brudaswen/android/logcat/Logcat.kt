package de.brudaswen.android.logcat

import android.content.Context
import androidx.room.Room
import de.brudaswen.android.logcat.database.LogcatDatabase
import de.brudaswen.android.logcat.database.LogcatExportDao
import de.brudaswen.android.logcat.database.LogcatImportDao
import de.brudaswen.android.logcat.database.LogcatSearchDao
import de.brudaswen.android.logcat.service.LogcatImportService

/**
 * Logcat Dependency Injection class that holds reference to [LogcatDatabase] and [LogcatImportService].
 */
public class Logcat(
    context: Context,
    databaseName: String = "logcat.db",
) {
    public val context: Context = context.applicationContext

    public val database: LogcatDatabase by lazy {
        Room.databaseBuilder(
            context = context,
            klass = LogcatDatabase::class.java,
            name = databaseName,
        ).build()
    }

    public val searchDao: LogcatSearchDao
        get() = database.searchDao

    public val importDao: LogcatImportDao
        get() = database.importDao

    public val exportDao: LogcatExportDao
        get() = database.exportDao

    public val service: LogcatImportService by lazy {
        LogcatImportService(
            dao = importDao,
        )
    }
}
