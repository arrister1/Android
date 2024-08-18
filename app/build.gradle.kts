plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.synrgy7team4.bankingapps"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.synrgy7team4.bankingapps"
        minSdk = 26
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
            buildConfigField("String", "BUILD_TYPE", "\"release\"")
        }
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            buildConfigField("String", "BUILD_FLAVOR", "\"debug\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    val libs = rootProject.extra["libs"] as Map<*, *>
    implementation(project(":common"))
    implementation(project(":di"))
    implementation(project(":domain"))
    implementation(project(":feature_auth"))
    implementation(project(":feature_mutasi"))
    implementation(project(":feature_dashboard"))
    implementation(project(":feature_transfer"))

    // Koin
    implementation(platform(libs["koin-bom"].toString()))
    implementation(libs["koin-android"].toString())

    // Navigation
    implementation(libs["navigation-fragment-ktx"].toString())
    implementation(libs["navigation-ui-ktx"].toString())

    // Generated dependencies
    implementation(libs["core-ktx"].toString())
    implementation(libs["appcompat"].toString())
    implementation(libs["material"].toString())
    implementation(libs["activity"].toString())
    testImplementation(libs["junit"].toString())
    androidTestImplementation(libs["ext-junit"].toString())
    androidTestImplementation(libs["espresso-core"].toString())
}