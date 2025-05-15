# Android Logcat Parser

[![Maven Central](https://img.shields.io/maven-central/v/de.brudaswen.android.logcat/logcat-core?style=flat-square)](https://search.maven.org/artifact/de.brudaswen.android.logcat/logcat-core)
[![Snapshot](https://img.shields.io/nexus/s/de.brudaswen.android.logcat/logcat-core?label=snapshot&server=https%3A%2F%2Foss.sonatype.org&style=flat-square)](https://oss.sonatype.org/#nexus-search;gav~de.brudaswen.android.logcat~logcat-core~~~)
[![CI Status](https://img.shields.io/github/actions/workflow/status/brudaswen/android-logcat/ci-main.yml?style=flat-square)](https://github.com/brudaswen/android-logcat/actions/workflows/ci-main.yml)
[![Codecov](https://img.shields.io/codecov/c/github/brudaswen/android-logcat?style=flat-square)](https://codecov.io/gh/brudaswen/android-logcat)
[![License](https://img.shields.io/github/license/brudaswen/android-logcat?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0)

Library to easily parse *Android Logcat*, store it in a database and export to `txt` or `csv`.

## Gradle Dependencies

```kotlin
// Plain Logcat Core (parser)
implementation("de.brudaswen.android.logcat:logcat-core:1.0.1")

// Android Logcat Database (Room database and import service)
implementation("de.brudaswen.android.logcat:logcat-database:1.0.1")

// Android Logcat Export (export database to txt file)
implementation("de.brudaswen.android.logcat:logcat-export:1.0.1")

// Android Logcat CSV Export (csv export extension)
implementation("de.brudaswen.android.logcat:logcat-export:1.0.1")
```

## Usage

### Core (Parser)

### Database and Import

### Export

#### CSV Export

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
