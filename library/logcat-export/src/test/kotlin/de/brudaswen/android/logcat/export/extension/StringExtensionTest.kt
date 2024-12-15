package de.brudaswen.android.logcat.export.extension

import kotlin.test.Test
import kotlin.test.assertEquals

internal class StringExtensionTest {
    @Test
    fun `given String is empty then truncate should return empty String`() {
        assertEquals("", "".truncateMiddle(maxLength = 2))
        assertEquals("", "".truncateMiddle(maxLength = 1))
    }

    @Test
    fun `given String is sorter then truncate should return original String`() {
        assertEquals("abcdef", "abcdef".truncateMiddle(maxLength = 7))
        assertEquals("abcdef", "abcdef".truncateMiddle(maxLength = 6))
    }

    @Test
    fun `given String is longer then truncate should truncate in the middle`() {
        assertEquals("ab…ef", "abcdef".truncateMiddle(maxLength = 5))
        assertEquals("ab…f", "abcdef".truncateMiddle(maxLength = 4))
        assertEquals("a…f", "abcdef".truncateMiddle(maxLength = 3))
        assertEquals("a…", "abcdef".truncateMiddle(maxLength = 2))
        assertEquals("…", "abcdef".truncateMiddle(maxLength = 1))
    }

    @Test
    fun `given String is longer then truncate should truncate in the middle with custom replacement`() {
        assertEquals("abc...hi", "abcdefghi".truncateMiddle(maxLength = 8, replacement = "..."))
        assertEquals("ab...hi", "abcdefghi".truncateMiddle(maxLength = 7, replacement = "..."))
        assertEquals("ab...i", "abcdefghi".truncateMiddle(maxLength = 6, replacement = "..."))
        assertEquals("a...i", "abcdefghi".truncateMiddle(maxLength = 5, replacement = "..."))
        assertEquals("a...", "abcdefghi".truncateMiddle(maxLength = 4, replacement = "..."))
        assertEquals("...", "abcdefghi".truncateMiddle(maxLength = 3, replacement = "..."))
    }

    @Test
    fun `given String is longer then truncate should truncate in the middle with empty replacement`() {
        assertEquals("abcdef", "abcdef".truncateMiddle(maxLength = 6, replacement = ""))
        assertEquals("abcef", "abcdef".truncateMiddle(maxLength = 5, replacement = ""))
        assertEquals("abef", "abcdef".truncateMiddle(maxLength = 4, replacement = ""))
        assertEquals("abf", "abcdef".truncateMiddle(maxLength = 3, replacement = ""))
        assertEquals("af", "abcdef".truncateMiddle(maxLength = 2, replacement = ""))
        assertEquals("a", "abcdef".truncateMiddle(maxLength = 1, replacement = ""))
        assertEquals("", "abcdef".truncateMiddle(maxLength = 0, replacement = ""))
    }
}
