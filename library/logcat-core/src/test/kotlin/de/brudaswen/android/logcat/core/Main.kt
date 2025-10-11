package de.brudaswen.android.logcat.core

import de.brudaswen.android.logcat.core.parser.LogcatBinaryParser
import kotlinx.coroutines.runBlocking
import java.io.BufferedInputStream

internal fun main() = runBlocking {
    val process = ProcessBuilder("adb", "logcat", "-B").start()

    LogcatBinaryParser(
        input = BufferedInputStream(process.inputStream),
    ).use { parser ->
        while (true) {
            val item = parser.parseItem()
            println(item)
        }
    }
}
