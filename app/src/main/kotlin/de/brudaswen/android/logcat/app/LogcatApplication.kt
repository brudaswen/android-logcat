package de.brudaswen.android.logcat.app

import android.app.Application
import de.brudaswen.android.logcat.Logcat
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

internal class LogcatApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val logcat = Logcat(applicationContext)
        MainScope().launch {
            logcat.service.start()
        }
    }
}
