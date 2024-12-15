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
