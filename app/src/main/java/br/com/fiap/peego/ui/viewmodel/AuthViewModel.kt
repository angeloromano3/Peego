package br.com.fiap.peego.ui.viewmodel


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.peego.data.auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    object Sucesso : AuthUiState()
    data class Erro(val mensagem: String) : AuthUiState()
}

class AuthViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(email: String, senha: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                repository.loginComEmailSenha(email, senha)
                _uiState.value = AuthUiState.Sucesso
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Erro(e.message ?: "Erro ao entrar")
            }
        }
    }

    fun criarConta(email: String, senha: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                repository.criarContaComEmailSenha(email, senha)
                _uiState.value = AuthUiState.Sucesso
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Erro(e.message ?: "Erro ao criar conta")
            }
        }
    }

    fun loginComGoogle(context: Context) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                repository.loginComGoogle(context)
                _uiState.value = AuthUiState.Sucesso
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Erro(e.message ?: "Erro ao entrar com Google")
            }
        }
    }

    fun resetarEstado() {
        _uiState.value = AuthUiState.Idle
    }
}