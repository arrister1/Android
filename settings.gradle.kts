pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Banking Apps"
include(":app")
include(":feature_auth")
include(":feature_auth:feature_auth_data")
include(":feature_auth:feature_auth_domain")
include(":feature_auth:feature_auth_presentation")
include(":feature_mutasi")
include(":feature_mutasi:feature_mutasi_data")
include(":feature_mutasi:feature_mutasi_domain")
include(":feature_mutasi:feature_mutasi_presentation")
include(":common")
include(":feature_mutasi:feature_mutasi_di")
include(":feature_auth:feature_auth_di")
