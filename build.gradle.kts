// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    kotlin("android") version(libs.versions.kotlin) apply false
    kotlin("plugin.serialization") version(libs.versions.kotlin) apply false
    alias(libs.plugins.dokka) apply false
    alias(libs.plugins.nexus.publish) apply false
    alias(libs.plugins.researchgate.release)
    alias(libs.plugins.nexus.staging)
    alias(libs.plugins.devtools.ksp) apply false
    alias(libs.plugins.androidx.room) apply false
}

allprojects {
    group = "de.brudaswen.android.logcat"
}

// Use `./gradlew release` to create a tagged release commit
release {
    preTagCommitMessage = "[Gradle Release Plugin] Release version"
    tagCommitMessage = "[Gradle Release Plugin] Release version"
    newVersionCommitMessage = "[Gradle Release Plugin] New version"

    git {
        requireBranch = "main"
    }
}

val mavenCentralUsername: String? by project
val mavenCentralPassword: String? by project
nexusStaging {
    packageGroup = "de.brudaswen"
    username = mavenCentralUsername
    password = mavenCentralPassword
    numberOfRetries = 60
    delayBetweenRetriesInMillis = 10_000
}
