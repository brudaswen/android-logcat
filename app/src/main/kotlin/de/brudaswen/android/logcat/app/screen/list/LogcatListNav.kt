package de.brudaswen.android.logcat.app.screen.list

import androidx.compose.runtime.Composable
import de.brudaswen.android.logcat.app.nav.LocalNavigator
import de.brudaswen.android.logcat.app.nav.Nav
import de.brudaswen.android.logcat.app.screen.details.LogcatDetailsNav
import de.brudaswen.android.logcat.ui.list.LogcatListScreen
import kotlinx.serialization.Serializable

@Serializable
internal class LogcatListNav : Nav {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        LogcatListScreen(
            onItemClick = { navigator.navigate(LogcatDetailsNav(it.uuid)) },
        )
    }
}
