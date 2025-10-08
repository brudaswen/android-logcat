plugins {
    kotlin("jvm")
    alias(libs.plugins.dokka.javadoc)
    `maven-publish`
    signing
    jacoco
}

kotlin {
    jvmToolchain(jdkVersion = 9)

    explicitApi()

    compilerOptions {
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
    archiveClassifier = "javadoc"
    from(tasks.dokkaGeneratePublicationJavadoc)
}

publishing {
    publications {
        create<MavenPublication>("library") {
            artifactId = "logcat-core"

            pom {
                name = "logcat-core"
                description = "Library to parse/export Logcat."
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
