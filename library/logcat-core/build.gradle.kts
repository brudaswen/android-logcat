plugins {
    kotlin("jvm")
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
