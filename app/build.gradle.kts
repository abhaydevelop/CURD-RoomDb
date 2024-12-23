plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
}

android {
    namespace = "com.example.curdapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.curdapplication"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    kapt {
        correctErrorTypes = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // add below dependency for using room.

    implementation ("androidx.room:room-runtime:2.6.0") // Use the latest stable version
    annotationProcessor ("androidx.room:room-compiler:2.6.0") // Required for annotation processing (for Java)

    // For Kotlin use kapt instead of annotationProcessor
    kapt ("androidx.room:room-compiler:2.6.0")

    // Optional, but recommended for RxJava
    implementation ("androidx.room:room-rxjava2:2.6.0")
    // If using Coroutine Support
    implementation ("androidx.room:room-ktx:2.6.0")


}