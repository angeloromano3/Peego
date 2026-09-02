package br.com.fiap.peego.ui.screens.explore

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import br.com.fiap.peego.R
import br.com.fiap.peego.ui.components.BottomNavBar
import br.com.fiap.peego.ui.components.BottomTab
import br.com.fiap.peego.ui.components.NearbyBathroomsSheet
import br.com.fiap.peego.ui.components.TopSearchBar
import br.com.fiap.peego.ui.map.OsmMapView

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
                        BottomTab.LISTA -> navController.navigate("lista")
                        BottomTab.CONTRIBUIR -> navController.navigate("contribuir")
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

            TopSearchBar(
                query = uiState.query,
                onQueryChange = viewModel::onQueryChange,
                onFilterClick = { navController.navigate("filtros") },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp)
            )

            IconButton(
                onClick = { },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
                    .shadow(4.dp, CircleShape)
                    .background(Color.White, CircleShape)
                    .size(44.dp)
            ) {
                Icon(
                    Icons.Filled.MyLocation,
                    contentDescription = stringResource(R.string.minha_localizacao),
                    tint = Color(0xFF1B1B1B),
                    modifier = Modifier.size(22.dp)
                )
            }

            NearbyBathroomsSheet(
                bathrooms = uiState.bathrooms,
                onListarClick = { navController.navigate("lista") },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}