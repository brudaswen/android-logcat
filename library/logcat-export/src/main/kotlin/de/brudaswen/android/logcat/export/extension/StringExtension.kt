package de.brudaswen.android.logcat.export.extension

import kotlin.math.roundToInt

/**
 * Truncate String in the middle and replace the middle chars with [replacement] such that the resulting String
 * satisfies [maxLength].
 *
 * @param maxLength The maximal length of the resulting String.
 * @param replacement The replacement String that is inserted in the middle.
 */
public fun String.truncateMiddle(
    maxLength: Int,
    replacement: String = "…",
): String =
    if (maxLength < replacement.length) {
        error("maxLength can not be smaller then replacement String.")
    } else if (length > maxLength) {
        val first = ((maxLength - replacement.length) / 2f).roundToInt()
        val last = maxLength - first - replacement.length

        substring(0 until first) + replacement + substring(length - last)
    } else {
        this
    }
