package de.brudaswen.android.logcat.ui.theme.extension

import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.runtime.Composable
import de.brudaswen.android.logcat.core.data.LogcatLevel
import de.brudaswen.android.logcat.ui.theme.ColorFamily
import de.brudaswen.android.logcat.ui.theme.LogcatTheme
import de.brudaswen.android.logcat.ui.util.cached

@Composable
public fun LogcatLevel.listItemColors(): ListItemColors = when (this) {
    LogcatLevel.Fatal -> ListItemDefaults.fatalColors()
    LogcatLevel.Error -> ListItemDefaults.errorColors()
    LogcatLevel.Warning -> ListItemDefaults.warningColors()
    LogcatLevel.Info -> ListItemDefaults.infoColors()
    LogcatLevel.Debug -> ListItemDefaults.debugColors()
    LogcatLevel.Verbose -> ListItemDefaults.verboseColors()
}

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
    colors(LogcatTheme.extendedColorScheme.error)
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
    overlineColor = colors.color,
)
