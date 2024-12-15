package de.brudaswen.android.logcat.core.data

public enum class LogcatLevel(public val identifier: String) {
    Fatal("WTF"),
    Error("E"),
    Warning("W"),
    Info("I"),
    Debug("D"),
    Verbose("V"),
}
