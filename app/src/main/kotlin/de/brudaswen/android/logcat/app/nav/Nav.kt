package de.brudaswen.android.logcat.app.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController

/**
 * Simple Compose [Nav] that defines a Compose screen and its Composable [Content] to render.
 */
internal interface Nav {
    @Composable
    fun Content()
}

/**
 * Access [NavController] to navigate.
 */
internal val LocalNavigator = staticCompositionLocalOf<NavController> {
    error("No Navigator found!")
}
