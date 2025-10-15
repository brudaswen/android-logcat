plugins {
    kotlin("android")
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ktlint)
    `maven-publish`
    signing
}

android {
    namespace = "de.brudaswen.android.logcat.ui"
    compileSdk = 36

    defaultConfig {
        minSdk = 11
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }

    buildFeatures {
        compose = true
    }
}

kotlin {
    jvmToolchain(jdkVersion = 9)

    explicitApi()

    compilerOptions {
        optIn.add("androidx.compose.material3.ExperimentalMaterial3Api")
        optIn.add("kotlin.time.ExperimentalTime")
        optIn.add("kotlin.uuid.ExperimentalUuidApi")
        optIn.add("kotlinx.coroutines.ExperimentalCoroutinesApi")
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    api(project(":library:logcat-export-csv"))

    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.viewmodel.compose)
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
            artifactId = "logcat-export-ui"

            pom {
                name = "logcat-export-ui"
                description = "Material3 Compose UI for viewing and exporting Logcat events."
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
                    developerConnection =
                        "scm:git:ssh://git@github.com:brudaswen/android-logcat.git"
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
