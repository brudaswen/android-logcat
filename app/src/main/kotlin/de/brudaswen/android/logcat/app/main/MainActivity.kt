package de.brudaswen.android.logcat.app.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.IntOffset
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import de.brudaswen.android.logcat.app.nav.LocalNavigator
import de.brudaswen.android.logcat.app.screen.details.LogcatDetailsNav
import de.brudaswen.android.logcat.app.screen.list.LogcatListNav
import de.brudaswen.android.logcat.app.theme.LogcatAppTheme

internal class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        setContent {
            LogcatAppTheme {
                val navController = rememberNavController()

                CompositionLocalProvider(
                    LocalNavigator provides navController,
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = LogcatListNav(),
                        enterTransition = { slideIn { IntOffset(it.width, 0) } },
                        exitTransition = { ExitTransition.None },
                        popEnterTransition = { EnterTransition.None },
                        popExitTransition = { slideOut { IntOffset(it.width, 0) } },
                    ) {
                        composable<LogcatListNav> {
                            it.toRoute<LogcatListNav>().Content()
                        }
                        composable<LogcatDetailsNav> {
                            it.toRoute<LogcatDetailsNav>().Content()
                        }
                    }
                }
            }
        }
    }
}
