package de.brudaswen.android.logcat.service

import de.brudaswen.android.logcat.core.cmd.ShellCommand
import de.brudaswen.android.logcat.core.data.LogcatItem
import de.brudaswen.android.logcat.core.parser.LogcatBinaryParser
import de.brudaswen.android.logcat.database.LogcatDatabase
import de.brudaswen.android.logcat.database.LogcatImportDao
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.invoke
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalDateTime.Formats
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Import service that periodically reads new [LogcatItems][LogcatItem] and writes
 * them into the [LogcatDatabase].
 */
public class LogcatImportService(
    private val dao: LogcatImportDao,
) {

    private var started = false

    private var running = CompletableDeferred<Unit>().also { it.complete(Unit) }

    /**
     * Start importing [LogcatItems][LogcatItem].
     *
     * @param pruneDatabase If `true` a database cleanup happens before import. If [startDate] is set all items before
     * [startDate] are removed; if not set all items from the database will be removed.
     * @param startDate The start date to import -- older items are ignored and not imported. Or `null` to import all
     * available Logcat items.
     * @param refreshRate How often the import of new items into the database happens.
     */
    public suspend fun start(
        pruneDatabase: Boolean = false,
        startDate: LocalDateTime? = null,
        refreshRate: Duration = 1.seconds,
    ): Unit = Dispatchers.IO {
        if (started) return@IO

        started = true
        running = CompletableDeferred()

        import(
            pruneDatabase = pruneDatabase,
            startDate = startDate,
            refreshRate = refreshRate,
        )

        running.complete(Unit)
    }

    /**
     * Stop the ongoing import and wait until fully stopped.
     */
    public suspend fun stop() {
        started = false
        running.await()
    }

    private suspend fun import(
        pruneDatabase: Boolean,
        startDate: LocalDateTime?,
        refreshRate: Duration,
    ) {
        if (pruneDatabase) {
            pruneDatabase(startDate)
        }

        while (started) {
            import(startDate)
            delay(refreshRate)
        }
    }

    private suspend fun import(startDate: LocalDateTime?) {
        val fromDate = listOfNotNull(
            startDate,
            dao.maxDate()?.toLocalDateTime(TimeZone.currentSystemDefault()),
        ).maxOrNull()

        val items = readItems(fromDate)

        storeItems(items)
    }

    private suspend fun readItems(fromDate: LocalDateTime?): List<LogcatItem> {
        val items = mutableListOf<LogcatItem>()
        ShellCommand(
            cmd = buildList {
                add("logcat")
                add("--binary")
                add("-d")

                if (fromDate != null) {
                    add("-t")
                    add(fromDate.format(Formats.Logcat))
                }
            },
        ).execute(
            readStdOut = { input ->
                val parser = LogcatBinaryParser(input)
                do {
                    val item = parser.parseItem()

                    if (item != null) {
                        items.add(item)
                    }
                } while (item != null)
            },
        )
        return items
    }

    private suspend fun storeItems(items: List<LogcatItem>) {
        dao.insertAll(items = items.toTypedArray())
    }

    private suspend fun pruneDatabase(startDate: LocalDateTime?) {
        if (startDate != null) {
            dao.clearBefore(startDate.toInstant(TimeZone.currentSystemDefault()))
        } else {
            dao.clear()
        }
    }
}

@Suppress("UnusedReceiverParameter")
private val Formats.Logcat
    get() = LocalDateTime.Format {
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
        secondFraction(1, 9)
    }
