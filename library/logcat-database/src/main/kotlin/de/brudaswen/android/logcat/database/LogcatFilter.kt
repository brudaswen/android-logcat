package de.brudaswen.android.logcat.database

import androidx.sqlite.SQLiteStatement
import de.brudaswen.android.logcat.core.data.LogcatLevel

public sealed class LogcatFilter : SqlCondition {

    /**
     * Filters with equal [orGroup] will be `OR` joined together.
     *
     * - Including filters can return `this::class` to be joined with other filters of the same
     *   type.
     * - Excluding filters can return `this` to be placed in a single group.
     */
    internal abstract val orGroup: Any

    public data class Search(
        val keyword: String,
    ) : LogcatFilter() {
        override val orGroup = this

        override val sql: String
            get() = listOfNotNull(
                "logcatItem.tag LIKE ?",
                "logcatItem.message LIKE ?",
                "logcatItem.tid = ?".takeIf { keyword.toIntOrNull() != null },
                "logcatItem.pid = ?".takeIf { keyword.toIntOrNull() != null },
            ).joinToString(
                prefix = "(",
                separator = " OR ",
                postfix = ")",
            )

        override fun bind(index: Int, statement: SQLiteStatement): Int {
            var index = index
            statement.bindText(index++, "%$keyword%")
            statement.bindText(index++, "%$keyword%")
            keyword.toIntOrNull()?.let {
                statement.bindInt(index++, it)
                statement.bindInt(index++, it)
            }
            return index
        }
    }

    public sealed class Tag : LogcatFilter() {
        public abstract val keyword: String
        public abstract val precision: QueryPrecision

        public data class Include(
            override val keyword: String,
            override val precision: QueryPrecision,
        ) : Tag() {
            override val orGroup = this::class

            override val sql: String
                get() = "logcatItem.tag LIKE ?"

            override fun bind(index: Int, statement: SQLiteStatement): Int =
                precision.bind(index, statement, keyword)
        }

        public data class Exclude(
            override val keyword: String,
            override val precision: QueryPrecision,
        ) : Tag() {
            override val orGroup = this

            override val sql: String
                get() = "logcatItem.tag NOT LIKE ?"

            override fun bind(index: Int, statement: SQLiteStatement): Int =
                precision.bind(index, statement, keyword)
        }
    }

    public sealed class Message : LogcatFilter() {
        public abstract val keyword: String
        public abstract val precision: QueryPrecision

        public data class Include(
            override val keyword: String,
            override val precision: QueryPrecision,
        ) : Message() {
            override val orGroup = this::class

            override val sql: String
                get() = "logcatItem.message LIKE ?"

            override fun bind(index: Int, statement: SQLiteStatement): Int =
                precision.bind(index, statement, keyword)
        }

        public data class Exclude(
            override val keyword: String,
            override val precision: QueryPrecision,
        ) : Message() {
            override val orGroup = this

            override val sql: String
                get() = "logcatItem.message NOT LIKE ?"

            override fun bind(index: Int, statement: SQLiteStatement): Int =
                precision.bind(index, statement, keyword)
        }
    }

    public data class Level(
        val level: LogcatLevel,
    ) : LogcatFilter() {
        override val orGroup = this::class

        override val sql: String
            get() = "logcatItem.level = ?"

        override fun bind(index: Int, statement: SQLiteStatement): Int {
            var index = index
            statement.bindText(index++, level.name)
            return index
        }
    }

    public enum class QueryPrecision {
        Exactly,
        Contains,
        ;

        internal fun bind(index: Int, statement: SQLiteStatement, query: String): Int {
            var index = index
            when (this) {
                Exactly -> statement.bindText(index++, query)
                Contains -> statement.bindText(index++, "%$query%")
            }
            return index
        }
    }
}
