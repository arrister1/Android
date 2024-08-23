plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    namespace = "com.synrgy7team4.feature_auth"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

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
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    kotlinOptions {
        jvmTarget = "1.8"

    }
}

dependencies {
    val libs = rootProject.extra["libs"] as Map<*, *>
    implementation(project(":common"))
    implementation(project(":di"))
    implementation(project(":domain"))

    // Koin
    implementation(platform(libs["koin-bom"].toString()))
    implementation(libs["koin-android"].toString())

    // Navigation
    implementation(libs["navigation-fragment-ktx"].toString())
    implementation(libs["navigation-ui-ktx"].toString())

    // Biometric
    implementation(libs["biometric"].toString())

    // Pin
    implementation(libs["pin-view"].toString())

    // Lottie
    implementation("com.airbnb.android:lottie:3.4.0")

    // ViewModel
    implementation(libs["lifecycle-viewmodel-ktx"].toString())

    // LiveData
    implementation(libs["lifecycle-livedata-ktx"].toString())

    // Lifecycle Compiler
    kapt(libs["lifecycle-compiler"].toString())

    // Generated dependencies
    implementation(libs["core-ktx"].toString())
    implementation(libs["appcompat"].toString())
    implementation(libs["material"].toString())
    implementation(libs["activity"].toString())
    implementation(libs["constraintlayout"].toString())
    testImplementation(libs["junit"].toString())
    androidTestImplementation(libs["ext-junit"].toString())
    androidTestImplementation(libs["espresso-core"].toString())
}