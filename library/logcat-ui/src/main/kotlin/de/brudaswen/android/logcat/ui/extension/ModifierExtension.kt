package de.brudaswen.android.logcat.ui.extension

import androidx.compose.ui.Modifier

internal inline fun Modifier.runIf(
    condition: Boolean,
    block: Modifier.() -> Modifier,
): Modifier = when {
    condition -> block()
    else -> this
}
