package de.brudaswen.android.logcat.core.uuid

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.uuid.Uuid

internal class UuidV5Test {
    @Test
    fun `Uuid v5 should be created`() {
        assertEquals(
            Uuid.parse("fedb2fa3-8f5c-5189-80e6-f563dd1cb8f9"),
            Uuid.v5(Uuid.parse("6ba7b811-9dad-11d1-80b4-00c04fd430c8"), "google.com"),
        )
    }
}
