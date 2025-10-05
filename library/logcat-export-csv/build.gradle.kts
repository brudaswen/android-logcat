plugins {
    alias(libs.plugins.android.library)
    kotlin("android")
    kotlin("plugin.serialization")
    `maven-publish`
    signing
}

android {
    namespace = "de.brudaswen.android.logcat.export.csv"
    compileSdk = 36

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

kotlin {
    jvmToolchain(jdkVersion = 9)

    compilerOptions {
        freeCompilerArgs.add("-Xexplicit-api=strict")

        optIn.add("kotlin.time.ExperimentalTime")
        optIn.add("kotlin.uuid.ExperimentalUuidApi")
        optIn.add("kotlinx.serialization.ExperimentalSerializationApi")
    }
}

dependencies {
    api(project(":library:logcat-export"))

    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.datetime)

    implementation(libs.kotlinx.serialization.csv)

    testImplementation(kotlin("test"))
}

tasks.withType<GenerateModuleMetadata> {
    enabled = !isSnapshot()
}

publishing {
    publications {
        register<MavenPublication>("release") {
            artifactId = "logcat-export-csv"

            pom {
                name.set("logcat-export-csv")
                description.set("Library to export Logcat events as CSV.")
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

signing {
    setRequired { !isSnapshot() }

    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)

    sign(publishing.publications["release"])
}

fun isSnapshot() = version.toString().endsWith("-SNAPSHOT")
