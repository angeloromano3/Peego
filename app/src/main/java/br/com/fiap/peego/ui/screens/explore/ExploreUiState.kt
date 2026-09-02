package br.com.fiap.peego.ui.screens.explore

import br.com.fiap.peego.model.Bathroom

data class ExploreUiState(
    val isLoading: Boolean = true,
    val query: String = "",
    val bathrooms: List<Bathroom> = emptyList()
)
