package de.brudaswen.android.logcat.app.screen.details

import androidx.compose.runtime.Composable
import de.brudaswen.android.logcat.app.nav.LocalNavigator
import de.brudaswen.android.logcat.app.nav.Nav
import de.brudaswen.android.logcat.ui.details.LogcatDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
internal data class LogcatDetailsNav(
    private val uuid: String,
) : Nav {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        LogcatDetailsScreen(
            uuid = uuid,
            onUpClick = navigator::navigateUp,
        )
    }
}
