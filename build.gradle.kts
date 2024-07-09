// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.3" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("androidx.room") version "2.6.1" apply false
    id ("androidx.navigation.safeargs.kotlin") version "2.5.1" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.24"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.24"
    kotlin("kapt") version "1.9.24"
    id("com.android.library") version "8.1.3" apply false
}