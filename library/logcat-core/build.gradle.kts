plugins {
    kotlin("jvm")
    alias(libs.plugins.dokka.javadoc)
    `maven-publish`
    signing
    jacoco
}

kotlin {
    jvmToolchain(jdkVersion = 9)

    compilerOptions {
        freeCompilerArgs.add("-Xexplicit-api=strict")

        optIn.add("kotlin.time.ExperimentalTime")
        optIn.add("kotlin.uuid.ExperimentalUuidApi")
    }
}

dependencies {
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.io.core)

    testImplementation(kotlin("test"))
}

java {
    withSourcesJar()
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<GenerateModuleMetadata> {
    enabled = !isSnapshot()
}

val dokkaJavadocJar by tasks.registering(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles Kotlin docs with Dokka"
    archiveClassifier.set("javadoc")
    from(tasks.dokkaGeneratePublicationJavadoc)
}

publishing {
    publications {
        create<MavenPublication>("library") {
            artifactId = "logcat-core"

            pom {
                name.set("logcat-core")
                description.set("Library to parse/export Logcat.")
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

            from(components["java"])
            artifact(dokkaJavadocJar)
        }
    }
}

signing {
    setRequired { !isSnapshot() }

    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)

    sign(publishing.publications["library"])
}

tasks.jacocoTestReport {
    reports {
        xml.required = true
        html.required = false
    }
}

tasks.check {
    dependsOn(tasks.jacocoTestReport)
}

fun isSnapshot() = version.toString().endsWith("-SNAPSHOT")
