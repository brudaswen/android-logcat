plugins {
    alias(libs.plugins.android.library)
    kotlin("android")
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.androidx.room)
    `maven-publish`
    signing
}

android {
    namespace = "de.brudaswen.android.logcat.database"
    compileSdk = 36

    room {
        schemaDirectory("$projectDir/schemas")
    }

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
    ksp(libs.androidx.room.compiler)

    api(project(":library:logcat-core"))

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    api(libs.androidx.room.paging)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.kotlinx.datetime)

    testImplementation(kotlin("test"))
}

tasks.withType<GenerateModuleMetadata> {
    enabled = !isSnapshot()
}

publishing {
    publications {
        register<MavenPublication>("release") {
            artifactId = "logcat-database"

            pom {
                name = "logcat-database"
                description = "Library to store Logcat events in Room database."
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
