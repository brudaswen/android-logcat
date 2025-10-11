package de.brudaswen.android.logcat.ui.theme

import androidx.compose.material3.CardColors
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.SelectableChipColors
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
public data class LogcatColorScheme(
    val fatal: ColorFamily,
    val error: ColorFamily,
    val warning: ColorFamily,
    val info: ColorFamily,
    val debug: ColorFamily,
    val verbose: ColorFamily,
) {
    internal var fatalListItemColorsCached: ListItemColors? = null
    internal var errorListItemColorsCached: ListItemColors? = null
    internal var warningListItemColorsCached: ListItemColors? = null
    internal var infoListItemColorsCached: ListItemColors? = null
    internal var debugListItemColorsCached: ListItemColors? = null
    internal var verboseListItemColorsCached: ListItemColors? = null

    internal var fatalCardColorsCached: CardColors? = null
    internal var errorCardColorsCached: CardColors? = null
    internal var warningCardColorsCached: CardColors? = null
    internal var infoCardColorsCached: CardColors? = null
    internal var debugCardColorsCached: CardColors? = null
    internal var verboseCardColorsCached: CardColors? = null

    internal var fatalFilterChipColorsCached: SelectableChipColors? = null
    internal var errorFilterChipColorsCached: SelectableChipColors? = null
    internal var warningFilterChipColorsCached: SelectableChipColors? = null
    internal var infoFilterChipColorsCached: SelectableChipColors? = null
    internal var debugFilterChipColorsCached: SelectableChipColors? = null
    internal var verboseFilterChipColorsCached: SelectableChipColors? = null
}

@Immutable
public data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color,
)

internal val LocalLogcatColorScheme = staticCompositionLocalOf { lightLogcatColorScheme() }

public fun lightLogcatColorScheme(
    fatal: Color = LogcatColorsLight.fatal,
    onFatal: Color = LogcatColorsLight.onFatal,
    fatalContainer: Color = LogcatColorsLight.fatalContainer,
    onFatalContainer: Color = LogcatColorsLight.onFatalContainer,
    error: Color = LogcatColorsLight.error,
    onError: Color = LogcatColorsLight.onError,
    errorContainer: Color = LogcatColorsLight.errorContainer,
    onErrorContainer: Color = LogcatColorsLight.onErrorContainer,
    warning: Color = LogcatColorsLight.warning,
    onWarning: Color = LogcatColorsLight.onWarning,
    warningContainer: Color = LogcatColorsLight.warningContainer,
    onWarningContainer: Color = LogcatColorsLight.onWarningContainer,
    info: Color = LogcatColorsLight.info,
    onInfo: Color = LogcatColorsLight.onInfo,
    infoContainer: Color = LogcatColorsLight.infoContainer,
    onInfoContainer: Color = LogcatColorsLight.onInfoContainer,
    debug: Color = LogcatColorsLight.debug,
    onDebug: Color = LogcatColorsLight.onDebug,
    debugContainer: Color = LogcatColorsLight.debugContainer,
    onDebugContainer: Color = LogcatColorsLight.onDebugContainer,
    verbose: Color = LogcatColorsLight.verbose,
    onVerbose: Color = LogcatColorsLight.onVerbose,
    verboseContainer: Color = LogcatColorsLight.verboseContainer,
    onVerboseContainer: Color = LogcatColorsLight.onVerboseContainer,
): LogcatColorScheme = LogcatColorScheme(
    fatal = ColorFamily(
        color = fatal,
        onColor = onFatal,
        colorContainer = fatalContainer,
        onColorContainer = onFatalContainer,
    ),
    error = ColorFamily(
        color = error,
        onColor = onError,
        colorContainer = errorContainer,
        onColorContainer = onErrorContainer,
    ),
    warning = ColorFamily(
        color = warning,
        onColor = onWarning,
        colorContainer = warningContainer,
        onColorContainer = onWarningContainer,
    ),
    info = ColorFamily(
        color = info,
        onColor = onInfo,
        colorContainer = infoContainer,
        onColorContainer = onInfoContainer,
    ),
    debug = ColorFamily(
        color = debug,
        onColor = onDebug,
        colorContainer = debugContainer,
        onColorContainer = onDebugContainer,
    ),
    verbose = ColorFamily(
        color = verbose,
        onColor = onVerbose,
        colorContainer = verboseContainer,
        onColorContainer = onVerboseContainer,
    ),
)

public fun darkLogcatColorScheme(
    fatal: Color = LogcatColorsDark.fatal,
    onFatal: Color = LogcatColorsDark.onFatal,
    fatalContainer: Color = LogcatColorsDark.fatalContainer,
    onFatalContainer: Color = LogcatColorsDark.onFatalContainer,
    error: Color = LogcatColorsDark.error,
    onError: Color = LogcatColorsDark.onError,
    errorContainer: Color = LogcatColorsDark.errorContainer,
    onErrorContainer: Color = LogcatColorsDark.onErrorContainer,
    warning: Color = LogcatColorsDark.warning,
    onWarning: Color = LogcatColorsDark.onWarning,
    warningContainer: Color = LogcatColorsDark.warningContainer,
    onWarningContainer: Color = LogcatColorsDark.onWarningContainer,
    info: Color = LogcatColorsDark.info,
    onInfo: Color = LogcatColorsDark.onInfo,
    infoContainer: Color = LogcatColorsDark.infoContainer,
    onInfoContainer: Color = LogcatColorsDark.onInfoContainer,
    debug: Color = LogcatColorsDark.debug,
    onDebug: Color = LogcatColorsDark.onDebug,
    debugContainer: Color = LogcatColorsDark.debugContainer,
    onDebugContainer: Color = LogcatColorsDark.onDebugContainer,
    verbose: Color = LogcatColorsDark.verbose,
    onVerbose: Color = LogcatColorsDark.onVerbose,
    verboseContainer: Color = LogcatColorsDark.verboseContainer,
    onVerboseContainer: Color = LogcatColorsDark.onVerboseContainer,
): LogcatColorScheme = LogcatColorScheme(
    fatal = ColorFamily(
        color = fatal,
        onColor = onFatal,
        colorContainer = fatalContainer,
        onColorContainer = onFatalContainer,
    ),
    error = ColorFamily(
        color = error,
        onColor = onError,
        colorContainer = errorContainer,
        onColorContainer = onErrorContainer,
    ),
    warning = ColorFamily(
        color = warning,
        onColor = onWarning,
        colorContainer = warningContainer,
        onColorContainer = onWarningContainer,
    ),
    info = ColorFamily(
        color = info,
        onColor = onInfo,
        colorContainer = infoContainer,
        onColorContainer = onInfoContainer,
    ),
    debug = ColorFamily(
        color = debug,
        onColor = onDebug,
        colorContainer = debugContainer,
        onColorContainer = onDebugContainer,
    ),
    verbose = ColorFamily(
        color = verbose,
        onColor = onVerbose,
        colorContainer = verboseContainer,
        onColorContainer = onVerboseContainer,
    ),
)
