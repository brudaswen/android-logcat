package de.brudaswen.android.logcat.ui.util

import kotlin.reflect.KMutableProperty0

internal inline fun <T> cached(
    property: KMutableProperty0<T?>,
    create: () -> T,
) = property.get() ?: create().also(property::set)
