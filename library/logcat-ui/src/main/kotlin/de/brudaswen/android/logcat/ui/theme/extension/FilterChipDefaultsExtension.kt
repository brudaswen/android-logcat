package de.brudaswen.android.logcat.ui.theme.extension

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.SelectableChipColors
import androidx.compose.runtime.Composable
import de.brudaswen.android.logcat.core.data.LogcatLevel
import de.brudaswen.android.logcat.ui.theme.ColorFamily
import de.brudaswen.android.logcat.ui.theme.LogcatTheme
import de.brudaswen.android.logcat.ui.util.cached

@Composable
public fun LogcatLevel.filterChipColors(): SelectableChipColors = when (this) {
    LogcatLevel.Fatal -> FilterChipDefaults.fatalColors()
    LogcatLevel.Error -> FilterChipDefaults.errorColors()
    LogcatLevel.Warning -> FilterChipDefaults.warningColors()
    LogcatLevel.Info -> FilterChipDefaults.infoColors()
    LogcatLevel.Debug -> FilterChipDefaults.debugColors()
    LogcatLevel.Verbose -> FilterChipDefaults.verboseColors()
}

@Composable
public fun LogcatLevel.filterChipBorder(selected: Boolean): BorderStroke = when (this) {
    LogcatLevel.Fatal -> FilterChipDefaults.fatalBorder(selected)
    LogcatLevel.Error -> FilterChipDefaults.errorBorder(selected)
    LogcatLevel.Warning -> FilterChipDefaults.warningBorder(selected)
    LogcatLevel.Info -> FilterChipDefaults.infoBorder(selected)
    LogcatLevel.Debug -> FilterChipDefaults.debugBorder(selected)
    LogcatLevel.Verbose -> FilterChipDefaults.verboseBorder(selected)
}

@Composable
public fun FilterChipDefaults.fatalColors(): SelectableChipColors = cached(
    property = LogcatTheme.extendedColorScheme::fatalFilterChipColorsCached,
) {
    colors(LogcatTheme.extendedColorScheme.fatal)
}

@Composable
public fun FilterChipDefaults.errorColors(): SelectableChipColors = cached(
    property = LogcatTheme.extendedColorScheme::errorFilterChipColorsCached,
) {
    colors(LogcatTheme.extendedColorScheme.error)
}

@Composable
public fun FilterChipDefaults.warningColors(): SelectableChipColors = cached(
    property = LogcatTheme.extendedColorScheme::warningFilterChipColorsCached,
) {
    colors(LogcatTheme.extendedColorScheme.warning)
}

@Composable
public fun FilterChipDefaults.infoColors(): SelectableChipColors = cached(
    property = LogcatTheme.extendedColorScheme::infoFilterChipColorsCached,
) {
    colors(LogcatTheme.extendedColorScheme.info)
}

@Composable
public fun FilterChipDefaults.debugColors(): SelectableChipColors = cached(
    property = LogcatTheme.extendedColorScheme::debugFilterChipColorsCached,
) {
    colors(LogcatTheme.extendedColorScheme.debug)
}

@Composable
public fun FilterChipDefaults.verboseColors(): SelectableChipColors = cached(
    property = LogcatTheme.extendedColorScheme::verboseFilterChipColorsCached,
) {
    colors(LogcatTheme.extendedColorScheme.verbose)
}

@Composable
private fun FilterChipDefaults.colors(colors: ColorFamily): SelectableChipColors = filterChipColors(
    labelColor = colors.color,
    iconColor = colors.color,
    selectedContainerColor = colors.colorContainer,
    selectedLabelColor = colors.onColorContainer,
    selectedLeadingIconColor = colors.onColorContainer,
)

@Composable
public fun FilterChipDefaults.fatalBorder(
    selected: Boolean,
): BorderStroke = border(
    selected = selected,
    colors = LogcatTheme.extendedColorScheme.fatal,
)

@Composable
public fun FilterChipDefaults.errorBorder(
    selected: Boolean,
): BorderStroke = border(
    selected = selected,
    colors = LogcatTheme.extendedColorScheme.error,
)

@Composable
public fun FilterChipDefaults.warningBorder(
    selected: Boolean,
): BorderStroke = border(
    selected = selected,
    colors = LogcatTheme.extendedColorScheme.warning,
)

@Composable
public fun FilterChipDefaults.infoBorder(
    selected: Boolean,
): BorderStroke = border(
    selected = selected,
    colors = LogcatTheme.extendedColorScheme.info,
)

@Composable
public fun FilterChipDefaults.debugBorder(
    selected: Boolean,
): BorderStroke = border(
    selected = selected,
    colors = LogcatTheme.extendedColorScheme.debug,
)

@Composable
public fun FilterChipDefaults.verboseBorder(
    selected: Boolean,
): BorderStroke = border(
    selected = selected,
    colors = LogcatTheme.extendedColorScheme.verbose,
)

@Composable
private fun FilterChipDefaults.border(
    selected: Boolean,
    colors: ColorFamily,
): BorderStroke = filterChipBorder(
    enabled = true,
    selected = selected,
    borderColor = colors.color,
)
