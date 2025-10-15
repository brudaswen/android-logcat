package de.brudaswen.android.logcat.database

import androidx.sqlite.SQLiteStatement

internal interface SqlCondition {
    val sql: String

    fun bind(index: Int, statement: SQLiteStatement): Int

    class Or(
        private val conditions: List<SqlCondition>,
    ) : SqlCondition {
        override val sql: String
            get() = conditions.joinToString(
                prefix = "(",
                separator = " OR ",
                postfix = ")",
                transform = SqlCondition::sql,
            )

        override fun bind(index: Int, statement: SQLiteStatement): Int =
            conditions.fold(index) { index, condition ->
                condition.bind(index, statement)
            }
    }
}
