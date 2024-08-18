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

    implementation("com.google.zxing:core:3.4.0")
//    implementation("com.journeyapps:zxing-android-embedded:4.2.0@aar")
    implementation("com.journeyapps:zxing-android-embedded:4.2.0@aar") {isTransitive = false}
    implementation(project(":shared"))
    implementation(project(":common"))
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.android.car.ui:car-ui-lib:2.6.0")

}