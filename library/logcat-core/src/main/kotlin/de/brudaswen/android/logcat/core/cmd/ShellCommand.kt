package de.brudaswen.android.logcat.core.cmd

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.invoke
import java.io.InputStream

/**
 * Create new shell command.
 *
 * @param cmd The command and its arguments.
 */
public class ShellCommand(
    private val cmd: List<String>,
) {

    /**
     * Execute this shell [cmd].
     *
     * @param readStdOut Lambda that reads the standard output stream.
     * @param readStdErr Lambda that reads the standard error stream.
     */
    public suspend fun execute(
        readStdOut: suspend (InputStream) -> Unit = {
            Dispatchers.IO {
                while (it.read() > -1) {
                    // Read input stream until EOF
                }
            }
        },
        readStdErr: suspend (InputStream) -> Unit = {
            Dispatchers.IO {
                while (it.read() > -1) {
                    // Read error stream until EOF
                }
            }
        },
    ) {
        Dispatchers.IO {
            val process = ProcessBuilder(cmd).start()

            awaitAll(
                async {
                    process.inputStream.buffered().use {
                        readStdOut(it)
                    }
                },
                async {
                    process.errorStream.buffered().use {
                        readStdErr(it)
                    }
                },
            )
        }
    }
}
