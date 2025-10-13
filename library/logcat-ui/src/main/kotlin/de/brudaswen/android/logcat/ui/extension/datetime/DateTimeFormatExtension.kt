package de.brudaswen.android.logcat.ui.extension.datetime

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.char

internal val dateTimeFormat = DateTimeComponents.Format {
    day()
    char('.')
    monthNumber()
    char('.')
    year()
    chars(", ")
    hour()
    char(':')
    minute()
    char(':')
    second()
    char('.')
    secondFraction(fixedLength = 3)
}

internal val dateFormat = LocalDateTime.Format {
    day()
    char('.')
    monthNumber()
    char('.')
    year()
}

internal val timeFormat = LocalDateTime.Format {
    hour()
    char(':')
    minute()
    char(':')
    second()
    char('.')
    secondFraction(fixedLength = 3)
}
