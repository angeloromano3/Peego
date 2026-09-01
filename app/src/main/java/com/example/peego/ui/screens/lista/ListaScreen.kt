package com.example.peego.ui.screens.lista

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.peego.navigation.Screen
import com.example.peego.ui.components.BathroomListItem
import com.example.peego.ui.components.BottomNavBar
import com.example.peego.ui.components.BottomTab
import com.example.peego.ui.screens.explore.ExploreViewModel

/** Tela "Lista": todos os banheiros próximos, sem o mapa. Reaproveita o mesmo ViewModel. */
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
                        BottomTab.EXPLORAR -> navController.popBackStack()
                        BottomTab.LISTA -> Unit
                        BottomTab.CONTRIBUIR -> navController.navigate(Screen.Contribuir.route)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(
                "Banheiros próximos",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(20.dp)
            )
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(uiState.bathrooms) { bathroom ->
                    BathroomListItem(bathroom = bathroom)
                }
            }
        }
    }
}
