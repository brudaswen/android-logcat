package de.brudaswen.android.logcat.app.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import de.brudaswen.android.logcat.ui.theme.LogcatTheme
import de.brudaswen.android.logcat.ui.theme.darkLogcatColorScheme
import de.brudaswen.android.logcat.ui.theme.lightLogcatColorScheme

@Composable
internal fun LogcatAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                when {
                    darkTheme -> dynamicDarkColorScheme(context)
                    else -> dynamicLightColorScheme(context)
                }
            }

            darkTheme -> darkScheme()

            else -> lightScheme()
        },
        typography = LogcatTypography,
    ) {
        LogcatTheme(
            extendedColorScheme = when {
                darkTheme -> darkLogcatColorScheme()
                else -> lightLogcatColorScheme()
            },
            content = content,
        )
    }
}

private fun lightScheme() = lightColorScheme(
    primary = LogcatColorsLight.primary,
    onPrimary = LogcatColorsLight.onPrimary,
    primaryContainer = LogcatColorsLight.primaryContainer,
    onPrimaryContainer = LogcatColorsLight.onPrimaryContainer,
    secondary = LogcatColorsLight.secondary,
    onSecondary = LogcatColorsLight.onSecondary,
    secondaryContainer = LogcatColorsLight.secondaryContainer,
    onSecondaryContainer = LogcatColorsLight.onSecondaryContainer,
    tertiary = LogcatColorsLight.tertiary,
    onTertiary = LogcatColorsLight.onTertiary,
    tertiaryContainer = LogcatColorsLight.tertiaryContainer,
    onTertiaryContainer = LogcatColorsLight.onTertiaryContainer,
    error = LogcatColorsLight.error,
    onError = LogcatColorsLight.onError,
    errorContainer = LogcatColorsLight.errorContainer,
    onErrorContainer = LogcatColorsLight.onErrorContainer,
    background = LogcatColorsLight.background,
    onBackground = LogcatColorsLight.onBackground,
    surface = LogcatColorsLight.surface,
    onSurface = LogcatColorsLight.onSurface,
    surfaceVariant = LogcatColorsLight.surfaceVariant,
    onSurfaceVariant = LogcatColorsLight.onSurfaceVariant,
    outline = LogcatColorsLight.outline,
    outlineVariant = LogcatColorsLight.outlineVariant,
    scrim = LogcatColorsLight.scrim,
    inverseSurface = LogcatColorsLight.inverseSurface,
    inverseOnSurface = LogcatColorsLight.inverseOnSurface,
    inversePrimary = LogcatColorsLight.inversePrimary,
    surfaceDim = LogcatColorsLight.surfaceDim,
    surfaceBright = LogcatColorsLight.surfaceBright,
    surfaceContainerLowest = LogcatColorsLight.surfaceContainerLowest,
    surfaceContainerLow = LogcatColorsLight.surfaceContainerLow,
    surfaceContainer = LogcatColorsLight.surfaceContainer,
    surfaceContainerHigh = LogcatColorsLight.surfaceContainerHigh,
    surfaceContainerHighest = LogcatColorsLight.surfaceContainerHighest,
)

private fun darkScheme() = darkColorScheme(
    primary = LogcatColorsDark.primary,
    onPrimary = LogcatColorsDark.onPrimary,
    primaryContainer = LogcatColorsDark.primaryContainer,
    onPrimaryContainer = LogcatColorsDark.onPrimaryContainer,
    secondary = LogcatColorsDark.secondary,
    onSecondary = LogcatColorsDark.onSecondary,
    secondaryContainer = LogcatColorsDark.secondaryContainer,
    onSecondaryContainer = LogcatColorsDark.onSecondaryContainer,
    tertiary = LogcatColorsDark.tertiary,
    onTertiary = LogcatColorsDark.onTertiary,
    tertiaryContainer = LogcatColorsDark.tertiaryContainer,
    onTertiaryContainer = LogcatColorsDark.onTertiaryContainer,
    error = LogcatColorsDark.error,
    onError = LogcatColorsDark.onError,
    errorContainer = LogcatColorsDark.errorContainer,
    onErrorContainer = LogcatColorsDark.onErrorContainer,
    background = LogcatColorsDark.background,
    onBackground = LogcatColorsDark.onBackground,
    surface = LogcatColorsDark.surface,
    onSurface = LogcatColorsDark.onSurface,
    surfaceVariant = LogcatColorsDark.surfaceVariant,
    onSurfaceVariant = LogcatColorsDark.onSurfaceVariant,
    outline = LogcatColorsDark.outline,
    outlineVariant = LogcatColorsDark.outlineVariant,
    scrim = LogcatColorsDark.scrim,
    inverseSurface = LogcatColorsDark.inverseSurface,
    inverseOnSurface = LogcatColorsDark.inverseOnSurface,
    inversePrimary = LogcatColorsDark.inversePrimary,
    surfaceDim = LogcatColorsDark.surfaceDim,
    surfaceBright = LogcatColorsDark.surfaceBright,
    surfaceContainerLowest = LogcatColorsDark.surfaceContainerLowest,
    surfaceContainerLow = LogcatColorsDark.surfaceContainerLow,
    surfaceContainer = LogcatColorsDark.surfaceContainer,
    surfaceContainerHigh = LogcatColorsDark.surfaceContainerHigh,
    surfaceContainerHighest = LogcatColorsDark.surfaceContainerHighest,
)
