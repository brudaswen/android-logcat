package de.brudaswen.android.logcat.ui.theme

import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import de.brudaswen.android.logcat.ui.util.cached

@Composable
public fun CardDefaults.fatalColors(): CardColors = cached(
    property = LogcatTheme.extendedColorScheme::fatalCardColorsCached,
) {
    cardColors(LogcatTheme.extendedColorScheme.fatal)
}

@Composable
public fun CardDefaults.errorColors(): CardColors = cached(
    property = LogcatTheme.extendedColorScheme::errorCardColorsCached,
) {
    cardColors(
        containerColor = LogcatTheme.colorScheme.errorContainer,
        contentColor = LogcatTheme.colorScheme.onErrorContainer,
    )
}

@Composable
public fun CardDefaults.warningColors(): CardColors = cached(
    property = LogcatTheme.extendedColorScheme::warningCardColorsCached,
) {
    cardColors(LogcatTheme.extendedColorScheme.warning)
}

@Composable
public fun CardDefaults.infoColors(): CardColors = cached(
    property = LogcatTheme.extendedColorScheme::infoCardColorsCached,
) {
    cardColors(LogcatTheme.extendedColorScheme.info)
}

@Composable
public fun CardDefaults.debugColors(): CardColors = cached(
    property = LogcatTheme.extendedColorScheme::debugCardColorsCached,
) {
    cardColors(LogcatTheme.extendedColorScheme.debug)
}

@Composable
public fun CardDefaults.verboseColors(): CardColors = cached(
    property = LogcatTheme.extendedColorScheme::verboseCardColorsCached,
) {
    cardColors(LogcatTheme.extendedColorScheme.verbose)
}

@Composable
private fun CardDefaults.cardColors(colors: ColorFamily): CardColors = cardColors(
    containerColor = colors.colorContainer,
    contentColor = colors.onColorContainer,
)
