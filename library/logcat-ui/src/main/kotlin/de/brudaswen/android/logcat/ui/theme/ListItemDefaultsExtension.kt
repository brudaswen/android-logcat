package de.brudaswen.android.logcat.ui.theme

import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.runtime.Composable
import de.brudaswen.android.logcat.ui.util.cached

@Composable
public fun ListItemDefaults.fatalColors(): ListItemColors = cached(
    property = LogcatTheme.extendedColorScheme::fatalListItemColorsCached,
) {
    colors(LogcatTheme.extendedColorScheme.fatal)
}

@Composable
public fun ListItemDefaults.errorColors(): ListItemColors = cached(
    property = LogcatTheme.extendedColorScheme::errorListItemColorsCached,
) {
    colors(
        containerColor = LogcatTheme.colorScheme.errorContainer,
        overlineColor = LogcatTheme.colorScheme.onErrorContainer,
        leadingIconColor = LogcatTheme.colorScheme.onErrorContainer,
    )
}

@Composable
public fun ListItemDefaults.warningColors(): ListItemColors = cached(
    property = LogcatTheme.extendedColorScheme::warningListItemColorsCached,
) {
    colors(LogcatTheme.extendedColorScheme.warning)
}

@Composable
public fun ListItemDefaults.infoColors(): ListItemColors = cached(
    property = LogcatTheme.extendedColorScheme::infoListItemColorsCached,
) {
    colors(LogcatTheme.extendedColorScheme.info)
}

@Composable
public fun ListItemDefaults.debugColors(): ListItemColors = cached(
    property = LogcatTheme.extendedColorScheme::debugListItemColorsCached,
) {
    colors(LogcatTheme.extendedColorScheme.debug)
}

@Composable
public fun ListItemDefaults.verboseColors(): ListItemColors = cached(
    property = LogcatTheme.extendedColorScheme::verboseListItemColorsCached,
) {
    colors(LogcatTheme.extendedColorScheme.verbose)
}

@Composable
private fun ListItemDefaults.colors(colors: ColorFamily): ListItemColors = colors(
    containerColor = colors.colorContainer,
    overlineColor = colors.onColorContainer,
    leadingIconColor = colors.onColorContainer,
)
