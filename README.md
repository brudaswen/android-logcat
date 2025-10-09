# Android Logcat Parser

[![Maven Central](https://img.shields.io/maven-central/v/de.brudaswen.android.logcat/logcat-core?style=flat-square)](https://search.maven.org/artifact/de.brudaswen.android.logcat/logcat-core)
[![Snapshot](https://img.shields.io/nexus/s/de.brudaswen.android.logcat/logcat-core?label=snapshot&server=https%3A%2F%2Foss.sonatype.org&style=flat-square)](https://oss.sonatype.org/#nexus-search;gav~de.brudaswen.android.logcat~logcat-core~~~)
[![CI Status](https://img.shields.io/github/actions/workflow/status/brudaswen/android-logcat/ci-main.yml?style=flat-square)](https://github.com/brudaswen/android-logcat/actions/workflows/ci-main.yml)
[![Codecov](https://img.shields.io/codecov/c/github/brudaswen/android-logcat?style=flat-square)](https://codecov.io/gh/brudaswen/android-logcat)
[![License](https://img.shields.io/github/license/brudaswen/android-logcat?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0)

Library to easily parse *Android Logcat*, store it in a database and export to `txt` or `csv`.

## Gradle Dependencies

```toml
[versions]
logcat = "3.1.0"

[libraries]
logcat-core = { group = "de.brudaswen.android.logcat", name = "logcat-core", version.ref = "logcat" }
logcat-database = { group = "de.brudaswen.android.logcat", name = "logcat-database", version.ref = "logcat" }
logcat-export = { group = "de.brudaswen.android.logcat", name = "logcat-export", version.ref = "logcat" }
logcat-export-csv = { group = "de.brudaswen.android.logcat", name = "logcat-export-csv", version.ref = "logcat" }
logcat-ui = { group = "de.brudaswen.android.logcat", name = "logcat-ui", version.ref = "logcat" }
```

```kotlin
// Plain Logcat Core (parser)
implementation(libs.logcat.core)

// Android Logcat Database (Room database and import service)
implementation(libs.logcat.database)

// Android Logcat Export (export database to txt file)
implementation(libs.logcat.export)

// Android Logcat CSV Export (csv export extension)
implementation(libs.logcat.export.csv)

// Android Material3 Compose Logcat UI
implementation(libs.logcat.ui)
```

## Usage

### Core (Parser)

The `LogcatBinaryParser` can be used on an arbitrary input stream to parse Logcat items.

```kotlin
fun main() = runBlocking {
    val process = ProcessBuilder("adb", "logcat", "-B").start()

    LogcatBinaryParser(
        input = BufferedInputStream(process.inputStream)
    ).use { parser ->
        while (true) {
            val item = parser.parseItem()
            println(item)
        }
    }
}
```

### Database and Import

If you want to provide a way to capture and store all Logcat messages of your app and represent
them inside your app, you can use the `LogcatDatabase` and the `LogcatImportService`.

Start the import service with:

```kotlin
val logcat = Logcat(context)
launch {
    logcat.service.start()
}
```

Search items via the `LogcatSearchDao`:

```kotlin
val logcat = Logcat(context)
val pagingSource = logcat.searchDao.getAllPaged()
```

### Export

The logcat items can get exported to a simple `txt` file.

```kotlin
val logcat = Logcat(context)
launch {
    LogcatExporter(logcat.exportDao).export(output)
}
```

#### CSV Export

The logcat items can also get exported as a CSV file.

```kotlin
val logcat = Logcat(context)
launch {
    LogcatExporter(
        dao = logcat.exportDao,
        serializer = LogcatCsvSerializer,
    ).export(output)
}
```

#### Material3 Compose UI

A simple Material3 Compose UI can be added that lists all Logcat items and allows to export them.

The `LogcatListScreen` shows a list of all Logcat items.

```kotlin
LogcatListScreen(
    onItemClick = { navigator.navigate(LogcatDetailsScreen(it.uuid)) },
)
```

The `LogcatDetailsScreen` shows the details of a single Logcat item including the full log message.

```kotlin
LogcatDetailsScreen(
    uuid = uuid,
    onUpClick = navigator::navigateUp,
)
```

Make sure that the the `androidx.compose.material3.MaterialTheme` is used, as well as the
`LogcatTheme`:

```kotlin
MaterialTheme {
    LogcatTheme {
        Logcat*Screen()
    }
}
```

## License

```
Copyright 2020 Sven Obser

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
