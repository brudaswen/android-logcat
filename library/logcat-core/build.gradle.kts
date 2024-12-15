import java.time.Duration

plugins {
    kotlin("jvm")
    alias(libs.plugins.dokka)
    alias(libs.plugins.nexus.publish)
    `maven-publish`
    signing
    jacoco
}

kotlin {
    jvmToolchain(jdkVersion = 8)

    compilerOptions {
        freeCompilerArgs.add("-Xexplicit-api=strict")

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

val dokkaJavadocJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles Kotlin docs with Dokka"
    archiveClassifier.set("javadoc")
    from(tasks.dokkaJavadoc)
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
