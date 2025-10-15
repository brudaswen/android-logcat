package de.brudaswen.android.logcat.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

public object LogcatTheme {
    public val colorScheme: ColorScheme
        @Composable @ReadOnlyComposable
        get() = MaterialTheme.colorScheme

    public val extendedColorScheme: LogcatColorScheme
        @Composable @ReadOnlyComposable
        get() = LocalLogcatColorScheme.current

    public val typography: Typography
        @Composable @ReadOnlyComposable
        get() = MaterialTheme.typography

    public val shapes: Shapes
        @Composable @ReadOnlyComposable
        get() = MaterialTheme.shapes
}

@Composable
public fun LogcatTheme(
    extendedColorScheme: LogcatColorScheme = LocalLogcatColorScheme.current,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalLogcatColorScheme provides extendedColorScheme,
        content = content,
    )
}

@Composable
internal fun LogcatPreviewTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme {
        LogcatTheme(
            extendedColorScheme = when {
                isSystemInDarkTheme() -> darkLogcatColorScheme()
                else -> lightLogcatColorScheme()
            },
            content = content,
        )
    }
}
