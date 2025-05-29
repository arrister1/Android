plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.synrgy7team4.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    val libs = rootProject.extra["libs"] as Map<*, *>
    implementation(project(":common"))
    implementation(project(":domain"))

    // Datastore
    implementation(libs["datastore-preferences"].toString())

    // Retrofit
    implementation(libs["retrofit"].toString())
    implementation(libs["converter-gson"].toString())

    // Interceptor
    implementation(libs["logging-interceptor"].toString())

    // Chucker
    debugImplementation(libs["chucker"].toString())
    releaseImplementation(libs["chucker-no-op"].toString())
}