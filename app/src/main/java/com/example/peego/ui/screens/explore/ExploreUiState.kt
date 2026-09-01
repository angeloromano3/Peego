package com.example.peego.ui.screens.explore

import com.example.peego.data.model.Bathroom

data class ExploreUiState(
    val isLoading: Boolean = true,
    val query: String = "",
    val bathrooms: List<Bathroom> = emptyList()
)
