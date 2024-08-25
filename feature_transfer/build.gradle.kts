plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}
apply(from = "../shared_dependencies.gradle")


android {
    namespace = "com.synrgy7team4.feature_transfer"
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

    // Navigation
    implementation(libs["navigation-fragment-ktx"].toString())
    implementation(libs["navigation-ui-ktx"].toString())

    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.android.car.ui:car-ui-lib:2.6.0")

    //testing
    testImplementation(libs["mockito-inline"].toString())
    testImplementation (libs["kotlinx-coroutines-test"].toString())
    testImplementation (libs["mockk-android"].toString())
    testImplementation (libs["mockk-agent"].toString())
    testImplementation (libs["core-testing"].toString())
    implementation (libs["slf4j-nop"].toString())
}