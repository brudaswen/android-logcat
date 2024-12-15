plugins {
    alias(libs.plugins.android.library)
    kotlin("android")
    kotlin("plugin.serialization")
}

android {
    namespace = "de.brudaswen.android.logcat.export"
    compileSdk = 35
}

kotlin {
    jvmToolchain(jdkVersion = 8)

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
