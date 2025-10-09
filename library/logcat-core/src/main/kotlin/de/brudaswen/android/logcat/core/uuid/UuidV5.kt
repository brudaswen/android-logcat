package de.brudaswen.android.logcat.core.uuid

import java.security.MessageDigest
import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.uuid.Uuid

/**
 * Generate namespace name-based [Uuid] (version 5).
 *
 * @param namespace The namespace [Uuid] to use.
 * @param name The name (String) to use.
 */
internal fun Uuid.Companion.v5(namespace: Uuid, name: String): Uuid =
    v5(namespace, name.toByteArray())

/**
 * Generate namespace name-based [Uuid] using SHA-1 (UUID version 5).
 *
 * @param namespace The namespace [Uuid] to use.
 * @param name The name (ByteArray) to use.
 */
internal fun Uuid.Companion.v5(namespace: Uuid, name: ByteArray): Uuid =
    fromByteArray(
        byteArray = MessageDigest.getInstance("SHA-1").apply {
            update(namespace.toByteArray())
            update(name)
        }.digest().apply {
            this[6] = (this[6] and 0x0f.toByte()) // clear version
            this[6] = (this[6] or 0x50.toByte()) // set to version 5
            this[8] = (this[8] and 0x3f.toByte()) // clear variant
            this[8] = (this[8] or 0x80.toByte()) // set to IETF variant
        }.copyOf(16),
    )
