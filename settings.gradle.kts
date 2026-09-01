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
        // osmdroid depende do jcenter antigo em alguns artefatos de cache de tiles;
        // o repositório principal já vem do mavenCentral.
    }
}

rootProject.name = "PeeGo"
include(":app")
