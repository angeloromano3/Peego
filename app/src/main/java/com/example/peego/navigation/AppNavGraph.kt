package com.example.peego.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.peego.ui.screens.contribuir.ContribuirScreen
import com.example.peego.ui.screens.explore.ExploreScreen
import com.example.peego.ui.screens.lista.ListaScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Explorar.route) {

        composable(Screen.Explorar.route) {
            ExploreScreen(navController = navController)
        }

        composable(Screen.Lista.route) {
            ListaScreen(navController = navController)
        }

        composable(Screen.Contribuir.route) {
            ContribuirScreen()
        }

        // Deep link de exemplo (testável via ADB):
        // adb shell am start -a android.intent.action.VIEW -d "peego://detalhe/1"
        composable(
            route = Screen.Detalhe.route,
            arguments = listOf(navArgument("bathroomId") { type = NavType.StringType }),
            deepLinks = listOf(navDeepLink { uriPattern = "peego://detalhe/{bathroomId}" })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("bathroomId") ?: ""
            // Tela de detalhe pode ser implementada reutilizando BathroomListItem + ViewModel.
            ListaScreen(navController = navController, highlightId = id)
        }
    }
}
