pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://www.jitpack.io" ) }

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://www.jitpack.io" ) }

    }
}

rootProject.name = "Banking Apps"
include(":app")

include(":common")
include(":shared")

include(":feature_auth")
include(":feature_mutasi")
include(":feature_dashboard")
include(":feature_transfer")

