package com.example.peego.ui.screens.explore

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.peego.data.local.AppDatabase
import com.example.peego.data.repository.BathroomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel (MVVM). Usa Coroutines + Flow para observar o Room de forma
 * reativa e expor um StateFlow único para a UI (ExploreUiState).
 */
class ExploreViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = BathroomRepository(
        AppDatabase.getInstance(application).bathroomDao()
    )

    private val query = MutableStateFlow("")
    private val isLoading = MutableStateFlow(true)

    val uiState: StateFlow<ExploreUiState> = combine(
        repository.nearbyBathrooms, query, isLoading
    ) { bathrooms, q, loading ->
        ExploreUiState(isLoading = loading, query = q, bathrooms = bathrooms)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ExploreUiState()
    )

    init {
        refresh()
    }

    fun onQueryChange(newQuery: String) {
        query.value = newQuery
    }

    fun refresh() {
        viewModelScope.launch {
            isLoading.value = true
            repository.refresh()
            isLoading.value = false
        }
    }
}
