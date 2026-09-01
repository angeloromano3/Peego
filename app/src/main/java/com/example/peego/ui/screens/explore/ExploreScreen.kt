package com.example.peego.ui.screens.explore

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.peego.navigation.Screen
import com.example.peego.ui.components.BottomNavBar
import com.example.peego.ui.components.BottomTab
import com.example.peego.ui.components.NearbyBathroomsSheet
import com.example.peego.ui.components.TopSearchBar
import com.example.peego.ui.map.OsmMapView

/**
 * Tela principal ("Explorar"), reproduzindo o layout de referência:
 * mapa em tela cheia + busca flutuante no topo + botão GPS + sheet
 * "Banheiros próximos" flutuando sobre o mapa + navegação inferior.
 */
@Composable
fun ExploreScreen(
    navController: NavHostController,
    viewModel: ExploreViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selected = BottomTab.EXPLORAR,
                onSelect = { tab ->
                    when (tab) {
                        BottomTab.EXPLORAR -> Unit
                        BottomTab.LISTA -> navController.navigate(Screen.Lista.route)
                        BottomTab.CONTRIBUIR -> navController.navigate(Screen.Contribuir.route)
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            // ── Mapa ──
            Crossfade(targetState = uiState.isLoading, label = "mapLoading") { loading ->
                if (loading) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    OsmMapView(
                        bathrooms = uiState.bathrooms,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // ── Barra de busca flutuante ──
            TopSearchBar(
                query = uiState.query,
                onQueryChange = viewModel::onQueryChange,
                onFilterClick = { /* TODO: abrir filtros */ },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp)
            )

            // ── Botão GPS / Minha localização ──
            IconButton(
                onClick = { /* TODO: centralizar no usuário */ },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
                    .shadow(4.dp, CircleShape)
                    .background(Color.White, CircleShape)
                    .size(44.dp)
            ) {
                Icon(
                    Icons.Filled.MyLocation,
                    contentDescription = "Minha localização",
                    tint = Color(0xFF1B1B1B),
                    modifier = Modifier.size(22.dp)
                )
            }

            // ── Sheet "Banheiros próximos" ──
            NearbyBathroomsSheet(
                bathrooms = uiState.bathrooms,
                onListarClick = { navController.navigate(Screen.Lista.route) },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
