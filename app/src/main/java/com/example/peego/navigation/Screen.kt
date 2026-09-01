package com.example.peego.navigation

/** Rotas de navegação do app (Navigation Compose). */
sealed class Screen(val route: String) {
    data object Explorar : Screen("explorar")
    data object Lista : Screen("lista")
    data object Contribuir : Screen("contribuir")

    // Rota com argumento, usada também como Deep Link:
    // peego://detalhe/{bathroomId}
    data object Detalhe : Screen("detalhe/{bathroomId}") {
        fun createRoute(bathroomId: String) = "detalhe/$bathroomId"
    }
}
