import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    kotlin("android") version libs.versions.kotlin apply false
    kotlin("plugin.serialization") version libs.versions.kotlin apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.androidx.room) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.devtools.ksp) apply false
    alias(libs.plugins.ktlint)
    alias(libs.plugins.nexus.publish)
    alias(libs.plugins.researchgate.release)
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

nexusPublishing {
    packageGroup = "de.brudaswen"

    clientTimeout = 5.minutes.toJavaDuration()

    repositories {
        sonatype {
            nexusUrl = uri("https://ossrh-staging-api.central.sonatype.com/service/local/")
            snapshotRepositoryUrl = uri("https://central.sonatype.com/repository/maven-snapshots/")
        }
    }
}
