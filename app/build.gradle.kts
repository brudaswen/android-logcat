plugins {
    alias(libs.plugins.android.application)
    kotlin("android")
}

android {
    namespace = "de.brudaswen.android.logcat.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "de.brudaswen.android.logcat.app"

        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    kotlin {
        jvmToolchain(jdkVersion = 9)
    }
}

dependencies {
    implementation(project(":library:logcat-export"))
    implementation(project(":library:logcat-export-csv"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}