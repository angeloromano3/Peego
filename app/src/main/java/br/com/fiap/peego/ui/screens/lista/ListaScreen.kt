package br.com.fiap.peego.ui.screens.lista

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import br.com.fiap.peego.ui.components.BottomNavBar
import br.com.fiap.peego.ui.components.BottomTab
import br.com.fiap.peego.ui.components.BathroomListItem
import br.com.fiap.peego.ui.screens.explore.ExploreViewModel

@Composable
fun ListaScreen(
    navController: NavHostController,
    highlightId: String? = null,
    viewModel: ExploreViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selected = BottomTab.LISTA,
                onSelect = { tab ->
                    when (tab) {
                        BottomTab.EXPLORAR -> navController.navigate("explorar") { popUpTo("explorar") { inclusive = true } }
                        BottomTab.LISTA -> Unit
                        BottomTab.CONTRIBUIR -> navController.navigate("contribuir")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(uiState.bathrooms) { bathroom ->
                BathroomListItem(bathroom = bathroom)
            }
        }
    }
}
