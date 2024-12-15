import java.time.Duration

plugins {
    alias(libs.plugins.android.library)
    kotlin("android")
    kotlin("plugin.serialization")
    alias(libs.plugins.nexus.publish)
    `maven-publish`
    signing
}

android {
    namespace = "de.brudaswen.android.logcat.export"
    compileSdk = 35
}

kotlin {
    jvmToolchain(jdkVersion = 9)

    compilerOptions {
        freeCompilerArgs.add("-Xexplicit-api=strict")

        optIn.add("kotlin.uuid.ExperimentalUuidApi")
    }
}

dependencies {
    api(project(":library:logcat-database"))

    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.datetime)

    testImplementation(kotlin("test"))
}

tasks.withType<GenerateModuleMetadata> {
    enabled = !isSnapshot()
}

val publishRelease = tasks.create("publishRelease") {
    description = "Publish to Maven Central (iff this is a release version)."
}

val publishSnapshot = tasks.create("publishSnapshot") {
    description = "Publish to Maven Central (iff this is a snapshot version)."
}

tasks.whenTaskAdded {
    if (name == "publishToSonatype") {
        val publishToSonatype = this
        if (!isSnapshot()) {
            publishRelease.dependsOn(publishToSonatype)

            val closeAndReleaseRepository = rootProject.tasks.getByName("closeAndReleaseRepository")
            closeAndReleaseRepository.mustRunAfter(publishToSonatype)
            publishRelease.dependsOn(closeAndReleaseRepository)
        } else {
            publishSnapshot.dependsOn(publishToSonatype)
        }
    }
}

publishing {
    publications {
        register<MavenPublication>("release") {
            artifactId = "logcat-export"

            pom {
                name.set("logcat-export")
                description.set("Library to export Logcat events.")
                url.set("https://github.com/brudaswen/android-logcat/")

                licenses {
                    license {
                        name.set("Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("brudaswen")
                        name.set("Sven Obser")
                        email.set("dev@brudaswen.de")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/brudaswen/android-logcat.git")
                    developerConnection.set("scm:git:ssh://git@github.com:brudaswen/android-logcat.git")
                    url.set("https://github.com/brudaswen/android-logcat/")
                }
                issueManagement {
                    system.set("GitHub Issues")
                    url.set("https://github.com/brudaswen/android-logcat/issues/")
                }
            }

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}

nexusPublishing {
    repositories {
        sonatype()
    }

    clientTimeout.set(Duration.ofMinutes(30))
    val useSnapshot: String? by project
    if (useSnapshot != null) {
        useStaging.set(useSnapshot?.toBoolean()?.not())
    }
}

signing {
    setRequired { !isSnapshot() }

    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)

    sign(publishing.publications["release"])
}

fun isSnapshot() = version.toString().endsWith("-SNAPSHOT")
