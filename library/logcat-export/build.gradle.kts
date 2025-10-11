plugins {
    kotlin("android")
    kotlin("plugin.serialization")
    alias(libs.plugins.android.library)
    alias(libs.plugins.ktlint)
    `maven-publish`
    signing
}

android {
    namespace = "de.brudaswen.android.logcat.export"
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

    explicitApi()

    @OptIn(org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation::class)
    abiValidation {
        enabled = true
    }

    compilerOptions {
        optIn.add("kotlin.time.ExperimentalTime")
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

publishing {
    publications {
        register<MavenPublication>("release") {
            artifactId = "logcat-export"

            pom {
                name = "logcat-export"
                description = "Library to export Logcat events."
                url = "https://github.com/brudaswen/android-logcat/"

                licenses {
                    license {
                        name = "Apache License, Version 2.0"
                        url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id = "brudaswen"
                        name = "Sven Obser"
                        email = "dev@brudaswen.de"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/brudaswen/android-logcat.git"
                    developerConnection = "scm:git:ssh://git@github.com:brudaswen/android-logcat.git"
                    url = "https://github.com/brudaswen/android-logcat/"
                }
                issueManagement {
                    system = "GitHub Issues"
                    url = "https://github.com/brudaswen/android-logcat/issues/"
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
